package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BibleHighlightEntity::class,
        ChurchEventEntity::class,
        VolunteerSignupEntity::class,
        AnnouncementEntity::class,
        MediaRecapEntity::class,
        SundaySchoolAttendanceEntity::class,
        GroupEntity::class,
        GroupMemberEntity::class,
        GroupMeetingLogEntity::class,
        GroupMessageEntity::class,
        DonationEntity::class,
        UserProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao
    abstract fun churchDao(): ChurchDao
    abstract fun sundaySchoolDao(): SundaySchoolDao
    abstract fun groupDao(): GroupDao
    abstract fun donationDao(): DonationDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bible_church_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
