package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity

object NotificationHelper {
    const val CHANNEL_DAILY_VERSE = "channel_daily_verse"
    const val CHANNEL_URGENT_PRAYER = "channel_urgent_prayer"
    const val CHANNEL_MINISTRY_EVENTS = "channel_ministry_events"
    const val CHANNEL_GROUP_ALERTS = "channel_group_alerts"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val dailyVerseChannel = NotificationChannel(
                CHANNEL_DAILY_VERSE,
                "Daily Verse Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily scripture inspiration and reflections"
            }

            val urgentPrayerChannel = NotificationChannel(
                CHANNEL_URGENT_PRAYER,
                "Urgent Prayer Requests & Announcements",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Urgent church alerts, schedule shifts, and prayer broadcasts"
                enableVibration(true)
            }

            val eventChannel = NotificationChannel(
                CHANNEL_MINISTRY_EVENTS,
                "Church Events & Service Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Upcoming gatherings, volunteer rosters, and Sunday school notices"
            }

            val groupChannel = NotificationChannel(
                CHANNEL_GROUP_ALERTS,
                "Prayer & Study Group Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Leadership updates, attendance check-ins, and encrypted group messages"
            }

            notificationManager.createNotificationChannels(
                listOf(dailyVerseChannel, urgentPrayerChannel, eventChannel, groupChannel)
            )
        }
    }

    fun showDailyVerseNotification(context: Context, verseRef: String, verseText: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 101, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_DAILY_VERSE)
            .setSmallIcon(android.R.drawable.ic_menu_agenda)
            .setContentTitle("Daily Verse: $verseRef")
            .setContentText(verseText)
            .setStyle(NotificationCompat.BigTextStyle().bigText("\"$verseText\"\n— $verseRef"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(1001, notification)
        } catch (_: SecurityException) {
            // Handled when permission is pending
        }
    }

    fun showUrgentBroadcastNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 102, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_URGENT_PRAYER)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("🚨 URGENT PRAYER / ANNOUNCEMENT: $title")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify((System.currentTimeMillis() % 100000).toInt(), notification)
        } catch (_: SecurityException) {
        }
    }

    fun showGroupLeaderNotification(context: Context, groupTitle: String, updateText: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 103, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_GROUP_ALERTS)
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setContentTitle("🔒 $groupTitle Update")
            .setContentText(updateText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(updateText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(2001, notification)
        } catch (_: SecurityException) {
        }
    }
}
