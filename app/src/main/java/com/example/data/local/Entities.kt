package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bible_highlights")
data class BibleHighlightEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: String,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val textSnippet: String,
    val colorHex: String,
    val note: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "church_events")
data class ChurchEventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val category: String,
    val rsvpCount: Int,
    val userRsvpStatus: String
)

@Entity(tableName = "volunteer_signups")
data class VolunteerSignupEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val eventTitle: String,
    val roleName: String,
    val description: String,
    val neededSlots: Int,
    val filledSlots: Int,
    val userSignedUp: Boolean,
    val timeSlot: String
)

@Entity(tableName = "church_announcements")
data class AnnouncementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val author: String,
    val authorRole: String,
    val category: String,
    val isUrgent: Boolean,
    val timestamp: Long
)

@Entity(tableName = "media_recaps")
data class MediaRecapEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val mediaType: String,
    val mediaUrl: String,
    val eventName: String,
    val date: String,
    val author: String,
    val timestamp: Long
)

@Entity(tableName = "ss_attendance")
data class SundaySchoolAttendanceEntity(
    @PrimaryKey val id: String,
    val classId: String,
    val studentName: String,
    val date: String,
    val status: String, // "PRESENT", "ABSENT", "EXCUSED"
    val note: String
)

@Entity(tableName = "study_groups")
data class GroupEntity(
    @PrimaryKey val id: String,
    val name: String,
    val focusBookOrTheme: String,
    val description: String,
    val leaderName: String,
    val leaderContact: String,
    val meetingDayTime: String,
    val meetingLocation: String,
    val memberCount: Int,
    val isUserLeader: Boolean,
    val isUserMember: Boolean,
    val pendingLeaderApproval: Boolean
)

@Entity(tableName = "group_members")
data class GroupMemberEntity(
    @PrimaryKey val id: String,
    val groupId: String,
    val memberName: String,
    val role: String,
    val isPresentAtLastMeeting: Boolean,
    val joinDate: String
)

@Entity(tableName = "group_meeting_logs")
data class GroupMeetingLogEntity(
    @PrimaryKey val id: String,
    val groupId: String,
    val date: String,
    val scriptureCovered: String,
    val attendeesCount: Int,
    val totalCount: Int,
    val prayerRequestsSummary: String
)

@Entity(tableName = "group_messages")
data class GroupMessageEntity(
    @PrimaryKey val id: String,
    val groupId: String,
    val senderName: String,
    val senderRole: String,
    val isFromMe: Boolean,
    val messageText: String,
    val timestamp: Long,
    val isEncrypted: Boolean,
    val encryptionKeyFingerprint: String
)

@Entity(tableName = "donations")
data class DonationEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val fundCategory: String,
    val paymentMethod: String,
    val frequency: String,
    val referenceCode: String,
    val timestamp: Long,
    val status: String
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val titleOrRole: String,
    val email: String,
    val phone: String,
    val homeChurch: String,
    val memberSince: String,
    val spiritualGiftsSerialized: String, // Comma-separated
    val favoriteVerse: String,
    val bio: String,
    val shareContactPhone: Boolean,
    val shareContactEmail: Boolean,
    val isGroupLeader: Boolean
)
