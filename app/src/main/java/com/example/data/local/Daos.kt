package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleDao {
    @Query("SELECT * FROM bible_highlights ORDER BY timestamp DESC")
    fun getAllHighlights(): Flow<List<BibleHighlightEntity>>

    @Query("SELECT * FROM bible_highlights WHERE bookId = :bookId AND chapter = :chapter")
    fun getHighlightsForChapter(bookId: String, chapter: Int): Flow<List<BibleHighlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: BibleHighlightEntity): Long

    @Query("DELETE FROM bible_highlights WHERE id = :id")
    suspend fun deleteHighlight(id: Long)

    @Query("DELETE FROM bible_highlights WHERE bookId = :bookId AND chapter = :chapter AND verseNumber = :verseNumber")
    suspend fun removeVerseHighlight(bookId: String, chapter: Int, verseNumber: Int)
}

@Dao
interface ChurchDao {
    // Events
    @Query("SELECT * FROM church_events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<ChurchEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<ChurchEventEntity>)

    @Query("UPDATE church_events SET userRsvpStatus = :status, rsvpCount = rsvpCount + :delta WHERE id = :eventId")
    suspend fun updateRsvp(eventId: String, status: String, delta: Int)

    // Volunteer
    @Query("SELECT * FROM volunteer_signups ORDER BY eventId ASC")
    fun getAllVolunteerRoles(): Flow<List<VolunteerSignupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVolunteerRoles(roles: List<VolunteerSignupEntity>)

    @Query("UPDATE volunteer_signups SET userSignedUp = :signedUp, filledSlots = filledSlots + :delta WHERE id = :roleId")
    suspend fun toggleVolunteerSignup(roleId: String, signedUp: Boolean, delta: Int)

    // Announcements
    @Query("SELECT * FROM church_announcements ORDER BY isUrgent DESC, timestamp DESC")
    fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncements(announcements: List<AnnouncementEntity>)

    // Media Recaps
    @Query("SELECT * FROM media_recaps ORDER BY timestamp DESC")
    fun getAllMediaRecaps(): Flow<List<MediaRecapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaRecap(recap: MediaRecapEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaRecaps(recaps: List<MediaRecapEntity>)
}

@Dao
interface SundaySchoolDao {
    @Query("SELECT * FROM ss_attendance WHERE classId = :classId ORDER BY studentName ASC")
    fun getAttendanceForClass(classId: String): Flow<List<SundaySchoolAttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceList(list: List<SundaySchoolAttendanceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSingleAttendance(record: SundaySchoolAttendanceEntity)
}

@Dao
interface GroupDao {
    @Query("SELECT * FROM study_groups")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(groups: List<GroupEntity>)

    @Query("UPDATE study_groups SET isUserMember = :isMember, pendingLeaderApproval = :pending, memberCount = memberCount + :countDelta WHERE id = :groupId")
    suspend fun updateGroupMembership(groupId: String, isMember: Boolean, pending: Boolean, countDelta: Int)

    @Query("UPDATE study_groups SET isUserLeader = :isLeader WHERE id = :groupId")
    suspend fun updateGroupLeaderRole(groupId: String, isLeader: Boolean)

    // Members
    @Query("SELECT * FROM group_members WHERE groupId = :groupId")
    fun getMembersForGroup(groupId: String): Flow<List<GroupMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupMembers(members: List<GroupMemberEntity>)

    @Query("DELETE FROM group_members WHERE id = :memberId")
    suspend fun removeGroupMember(memberId: String)

    // Meeting Logs
    @Query("SELECT * FROM group_meeting_logs WHERE groupId = :groupId ORDER BY date DESC")
    fun getMeetingLogsForGroup(groupId: String): Flow<List<GroupMeetingLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetingLog(log: GroupMeetingLogEntity)

    // Chat messages
    @Query("SELECT * FROM group_messages WHERE groupId = :groupId ORDER BY timestamp ASC")
    fun getMessagesForGroup(groupId: String): Flow<List<GroupMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: GroupMessageEntity)
}

@Dao
interface DonationDao {
    @Query("SELECT * FROM donations ORDER BY timestamp DESC")
    fun getAllDonations(): Flow<List<DonationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonation(donation: DonationEntity)
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun getUserProfile(id: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)
}
