package com.example.data.model

data class StudyGroup(
    val id: String,
    val name: String,
    val focusBookOrTheme: String,
    val description: String,
    val leaderName: String,
    val leaderContact: String,
    val meetingDayTime: String,
    val meetingLocation: String,
    val memberCount: Int,
    val isUserLeader: Boolean = false,
    val isUserMember: Boolean = false,
    val pendingLeaderApproval: Boolean = false
)

data class GroupMemberItem(
    val id: String,
    val groupId: String,
    val memberName: String,
    val role: String, // "Leader", "Assistant Leader", "Member", "Visitor"
    val isPresentAtLastMeeting: Boolean = true,
    val joinDate: String
)

data class GroupMeetingLog(
    val id: String,
    val groupId: String,
    val date: String,
    val scriptureCovered: String,
    val attendeesCount: Int,
    val totalCount: Int,
    val prayerRequestsSummary: String
)

data class EncryptedMessage(
    val id: String,
    val groupId: String,
    val senderName: String,
    val senderRole: String,
    val isFromMe: Boolean,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isEncrypted: Boolean = true,
    val encryptionKeyFingerprint: String = "AES-256-GCM / 8F4E:91BA"
)
