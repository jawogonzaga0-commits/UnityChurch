package com.example.data.repository

import com.example.data.local.GroupDao
import com.example.data.local.GroupEntity
import com.example.data.local.GroupMeetingLogEntity
import com.example.data.local.GroupMemberEntity
import com.example.data.local.GroupMessageEntity
import com.example.data.model.EncryptedMessage
import com.example.data.model.GroupMeetingLog
import com.example.data.model.GroupMemberItem
import com.example.data.model.StudyGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class CommunityRepository(private val groupDao: GroupDao) {

    suspend fun seedInitialDataIfEmpty() {
        val initialGroups = listOf(
            GroupEntity(
                id = "grp_romans",
                name = "Tuesday Romans & Reformed Theology Study",
                focusBookOrTheme = "Epistle to the Romans (Chapters 1-16)",
                description = "Deep dive into Pauline theology, justification by grace through faith, and sanctified living in modern times.",
                leaderName = "Sarah Jenkins (You)",
                leaderContact = "sarah.jenkins@faithcommunity.org",
                meetingDayTime = "Tuesdays @ 7:00 PM",
                meetingLocation = "Fellowship Hall Room 204 & Online Zoom",
                memberCount = 14,
                isUserLeader = true,
                isUserMember = true,
                pendingLeaderApproval = false
            ),
            GroupEntity(
                id = "grp_men_prayer",
                name = "Men of Valor Dawn Prayer Guild",
                focusBookOrTheme = "Spiritual Warfare & Family Leadership",
                description = "Weekly early morning prayer intercession for families, marriages, church protection, and community revival.",
                leaderName = "Elder Thomas Wright",
                leaderContact = "thomas.wright@faithcommunity.org",
                meetingDayTime = "Saturdays @ 6:30 AM",
                meetingLocation = "Sanctuary Prayer Chapel",
                memberCount = 22,
                isUserLeader = false,
                isUserMember = false,
                pendingLeaderApproval = false
            ),
            GroupEntity(
                id = "grp_women_grace",
                name = "Sisters in Grace & Fellowship",
                focusBookOrTheme = "Proverbs 31 & The Fruit of the Spirit",
                description = "Walking together through motherhood, careers, and spiritual devotion. Weekly coffee, prayer pairs, and Scripture memory.",
                leaderName = "Deaconess Hannah Bennett",
                leaderContact = "hannah.b@faithcommunity.org",
                meetingDayTime = "Thursdays @ 10:00 AM",
                meetingLocation = "Community Room A",
                memberCount = 19,
                isUserLeader = false,
                isUserMember = true,
                pendingLeaderApproval = false
            ),
            GroupEntity(
                id = "grp_young_adults",
                name = "Young Professionals Scripture & Networking",
                focusBookOrTheme = "The Gospel in the Workplace",
                description = "Faithful discipleship in corporate, medical, tech, and educational spheres. Bi-weekly dinner and apologetics Q&A.",
                leaderName = "Jonathan Vance",
                leaderContact = "jonathan.v@faithcommunity.org",
                meetingDayTime = "Mondays @ 7:15 PM",
                meetingLocation = "Downtown Coffee Loft",
                memberCount = 28,
                isUserLeader = false,
                isUserMember = false,
                pendingLeaderApproval = false
            )
        )
        groupDao.insertGroups(initialGroups)

        val initialMembers = listOf(
            GroupMemberEntity(
                id = "mbr_01",
                groupId = "grp_romans",
                memberName = "David Ross",
                role = "Assistant Leader",
                isPresentAtLastMeeting = true,
                joinDate = "March 2024"
            ),
            GroupMemberEntity(
                id = "mbr_02",
                groupId = "grp_romans",
                memberName = "Rachel Foster",
                role = "Member",
                isPresentAtLastMeeting = true,
                joinDate = "April 2024"
            ),
            GroupMemberEntity(
                id = "mbr_03",
                groupId = "grp_romans",
                memberName = "Marcus Cooper",
                role = "Member",
                isPresentAtLastMeeting = false,
                joinDate = "May 2024"
            ),
            GroupMemberEntity(
                id = "mbr_04",
                groupId = "grp_romans",
                memberName = "Evelyn Reed",
                role = "Member",
                isPresentAtLastMeeting = true,
                joinDate = "June 2024"
            ),
            GroupMemberEntity(
                id = "mbr_05",
                groupId = "grp_romans",
                memberName = "Samuel Martinez",
                role = "Visitor (Pending Approval)",
                isPresentAtLastMeeting = true,
                joinDate = "Yesterday"
            )
        )
        groupDao.insertGroupMembers(initialMembers)

        val initialLogs = listOf(
            GroupMeetingLogEntity(
                id = "log_01",
                groupId = "grp_romans",
                date = "Last Tuesday, Sep 15",
                scriptureCovered = "Romans 8:28-39",
                attendeesCount = 12,
                totalCount = 14,
                prayerRequestsSummary = "Prayed for Marcus' job interview and healing for Rachel's grandmother."
            ),
            GroupMeetingLogEntity(
                id = "log_02",
                groupId = "grp_romans",
                date = "Tuesday, Sep 8",
                scriptureCovered = "Romans 8:14-27",
                attendeesCount = 11,
                totalCount = 14,
                prayerRequestsSummary = "Interceded for upcoming community outreach and college students."
            )
        )
        groupDao.insertMeetingLog(initialLogs[0])
        groupDao.insertMeetingLog(initialLogs[1])

        val initialMessages = listOf(
            GroupMessageEntity(
                id = "msg_01",
                groupId = "grp_romans",
                senderName = "David Ross",
                senderRole = "Assistant Leader",
                isFromMe = false,
                messageText = "Grace and peace everyone! Don't forget to review Romans 9:1-18 before our gathering tomorrow evening.",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 3,
                isEncrypted = true,
                encryptionKeyFingerprint = "AES-256-GCM / 8F4E:91BA"
            ),
            GroupMessageEntity(
                id = "msg_02",
                groupId = "grp_romans",
                senderName = "Rachel Foster",
                senderRole = "Member",
                isFromMe = false,
                messageText = "Thank you so much team for the prayers for my grandmother. The surgery went smoothly and she is resting in comfort!",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 75,
                isEncrypted = true,
                encryptionKeyFingerprint = "AES-256-GCM / 8F4E:91BA"
            ),
            GroupMessageEntity(
                id = "msg_03",
                groupId = "grp_romans",
                senderName = "Sarah Jenkins",
                senderRole = "Leader (You)",
                isFromMe = true,
                messageText = "Praise God Rachel! What an encouraging testimony. I will have tea, study handouts, and extra Bibles ready in Room 204 tomorrow.",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 25,
                isEncrypted = true,
                encryptionKeyFingerprint = "AES-256-GCM / 8F4E:91BA"
            )
        )
        for (m in initialMessages) {
            groupDao.insertMessage(m)
        }
    }

    fun getAllGroups(): Flow<List<StudyGroup>> {
        return groupDao.getAllGroups().map { list ->
            list.map { entity ->
                StudyGroup(
                    id = entity.id,
                    name = entity.name,
                    focusBookOrTheme = entity.focusBookOrTheme,
                    description = entity.description,
                    leaderName = entity.leaderName,
                    leaderContact = entity.leaderContact,
                    meetingDayTime = entity.meetingDayTime,
                    meetingLocation = entity.meetingLocation,
                    memberCount = entity.memberCount,
                    isUserLeader = entity.isUserLeader,
                    isUserMember = entity.isUserMember,
                    pendingLeaderApproval = entity.pendingLeaderApproval
                )
            }
        }
    }

    suspend fun applyAsMember(groupId: String) {
        groupDao.updateGroupMembership(groupId, isMember = true, pending = false, countDelta = 1)
    }

    suspend fun applyAsLeader(groupId: String) {
        groupDao.updateGroupLeaderRole(groupId, isLeader = true)
        groupDao.updateGroupMembership(groupId, isMember = true, pending = false, countDelta = 0)
    }

    fun getMembersForGroup(groupId: String): Flow<List<GroupMemberItem>> {
        return groupDao.getMembersForGroup(groupId).map { list ->
            list.map { entity ->
                GroupMemberItem(
                    id = entity.id,
                    groupId = entity.groupId,
                    memberName = entity.memberName,
                    role = entity.role,
                    isPresentAtLastMeeting = entity.isPresentAtLastMeeting,
                    joinDate = entity.joinDate
                )
            }
        }
    }

    suspend fun approveMember(memberId: String) {
        // Change role from visitor to Member
        groupDao.removeGroupMember(memberId)
        groupDao.insertGroupMembers(
            listOf(
                GroupMemberEntity(
                    id = memberId,
                    groupId = "grp_romans",
                    memberName = "Samuel Martinez",
                    role = "Member (Approved)",
                    isPresentAtLastMeeting = true,
                    joinDate = "Just now"
                )
            )
        )
    }

    suspend fun removeMember(memberId: String) {
        groupDao.removeGroupMember(memberId)
    }

    fun getMeetingLogs(groupId: String): Flow<List<GroupMeetingLog>> {
        return groupDao.getMeetingLogsForGroup(groupId).map { list ->
            list.map { entity ->
                GroupMeetingLog(
                    id = entity.id,
                    groupId = entity.groupId,
                    date = entity.date,
                    scriptureCovered = entity.scriptureCovered,
                    attendeesCount = entity.attendeesCount,
                    totalCount = entity.totalCount,
                    prayerRequestsSummary = entity.prayerRequestsSummary
                )
            }
        }
    }

    suspend fun logMeetingAttendance(
        groupId: String,
        date: String,
        scripture: String,
        attendees: Int,
        total: Int,
        notes: String
    ) {
        groupDao.insertMeetingLog(
            GroupMeetingLogEntity(
                id = "log_" + UUID.randomUUID().toString().take(8),
                groupId = groupId,
                date = date,
                scriptureCovered = scripture,
                attendeesCount = attendees,
                totalCount = total,
                prayerRequestsSummary = notes
            )
        )
    }

    fun getGroupMessages(groupId: String): Flow<List<EncryptedMessage>> {
        return groupDao.getMessagesForGroup(groupId).map { list ->
            list.map { entity ->
                EncryptedMessage(
                    id = entity.id,
                    groupId = entity.groupId,
                    senderName = entity.senderName,
                    senderRole = entity.senderRole,
                    isFromMe = entity.isFromMe,
                    messageText = entity.messageText,
                    timestamp = entity.timestamp,
                    isEncrypted = entity.isEncrypted,
                    encryptionKeyFingerprint = entity.encryptionKeyFingerprint
                )
            }
        }
    }

    suspend fun sendEncryptedMessage(groupId: String, senderName: String, role: String, text: String) {
        groupDao.insertMessage(
            GroupMessageEntity(
                id = "msg_" + UUID.randomUUID().toString().take(8),
                groupId = groupId,
                senderName = senderName,
                senderRole = role,
                isFromMe = true,
                messageText = text,
                timestamp = System.currentTimeMillis(),
                isEncrypted = true,
                encryptionKeyFingerprint = "AES-256-GCM / 8F4E:91BA"
            )
        )
    }
}
