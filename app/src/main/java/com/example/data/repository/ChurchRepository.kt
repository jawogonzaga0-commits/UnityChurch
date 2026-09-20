package com.example.data.repository

import com.example.data.local.AnnouncementEntity
import com.example.data.local.ChurchDao
import com.example.data.local.ChurchEventEntity
import com.example.data.local.MediaRecapEntity
import com.example.data.local.VolunteerSignupEntity
import com.example.data.model.ChurchAnnouncement
import com.example.data.model.ChurchEvent
import com.example.data.model.ChurchProfile
import com.example.data.model.MediaRecap
import com.example.data.model.VolunteerRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class ChurchRepository(private val churchDao: ChurchDao) {

    val churchProfile = ChurchProfile(
        name = "Grace & Truth Community Church",
        denomination = "Non-Denominational Christian Fellowship",
        seniorPastor = "Pastor David & Eleanor Bennett",
        address = "742 Cornerstone Blvd, Springfield",
        phone = "+1 (555) 782-4431",
        email = "office@graceandtruth.church",
        website = "www.graceandtruth.church",
        serviceTimes = listOf(
            "Sunday Celebration: 9:00 AM & 11:15 AM",
            "Sunday School & Bible Classes: 10:15 AM",
            "Wednesday Night Prayer & Word: 7:00 PM",
            "Saturday Morning Men's & Women's Prayer: 8:00 AM"
        ),
        missionStatement = "Rooted in the living Word, walking in unconditional love, and equipping the saints to transform our city for Christ.",
        establishedYear = "1994"
    )

    suspend fun seedInitialDataIfEmpty() {
        val initialEvents = listOf(
            ChurchEventEntity(
                id = "evt_01",
                title = "Sunday Worship Celebration & Communion",
                date = "Tomorrow, 9:00 AM",
                time = "9:00 AM - 10:45 AM",
                location = "Main Sanctuary & Livestream",
                description = "Join our parish for uplifting choral worship, verse-by-verse preaching from Romans 8, and the celebration of Holy Communion.",
                category = "Worship Service",
                rsvpCount = 142,
                userRsvpStatus = "Attending"
            ),
            ChurchEventEntity(
                id = "evt_02",
                title = "Midweek Community Dinner & Bible Study",
                date = "Wednesday, 6:30 PM",
                time = "6:30 PM - 8:30 PM",
                location = "Fellowship Hall",
                description = "Fellowship meal followed by breakout tables on 'The Covenants of God'. Families and all age groups welcome.",
                category = "Fellowship",
                rsvpCount = 58,
                userRsvpStatus = "Interested"
            ),
            ChurchEventEntity(
                id = "evt_03",
                title = "City Outreach & Food Bank Distribution",
                date = "Saturday, 8:00 AM",
                time = "8:00 AM - 1:00 PM",
                location = "Eastside Community Center",
                description = "Distributing fresh groceries, prayer support, and children's school packs to 300+ local families in need.",
                category = "Community Outreach",
                rsvpCount = 34,
                userRsvpStatus = "None"
            ),
            ChurchEventEntity(
                id = "evt_04",
                title = "All-Night Parish Prayer Vigil",
                date = "Friday, 9:00 PM",
                time = "9:00 PM - 5:00 AM",
                location = "Prayer Chapel",
                description = "Unbroken intercession for our congregation, revival in youth, city leaders, and healing for the sick.",
                category = "Prayer Vigil",
                rsvpCount = 26,
                userRsvpStatus = "None"
            ),
            ChurchEventEntity(
                id = "evt_05",
                title = "Ignite Youth Encounter Night",
                date = "Next Sunday, 6:00 PM",
                time = "6:00 PM - 8:30 PM",
                location = "Youth Pavilion",
                description = "High-energy worship, relevant message on identity in Christ, games, and pizza fellowship for grades 6-12.",
                category = "Youth",
                rsvpCount = 47,
                userRsvpStatus = "Attending"
            )
        )
        churchDao.insertEvents(initialEvents)

        val initialVolunteers = listOf(
            VolunteerSignupEntity(
                id = "vol_01",
                eventId = "evt_01",
                eventTitle = "Sunday Worship Celebration",
                roleName = "Sanctuary Greeter & Welcome Team",
                description = "Warmly welcome families, distribute worship bulletins, and guide newcomers to seating.",
                neededSlots = 6,
                filledSlots = 4,
                userSignedUp = true,
                timeSlot = "8:30 AM - 10:45 AM"
            ),
            VolunteerSignupEntity(
                id = "vol_02",
                eventId = "evt_01",
                eventTitle = "Sunday Worship Celebration",
                roleName = "Audio / Visual & Stream Technician",
                description = "Operate sanctuary projection lyrics, sound levels, and multi-camera live broadcast.",
                neededSlots = 3,
                filledSlots = 2,
                userSignedUp = false,
                timeSlot = "8:15 AM - 12:45 PM"
            ),
            VolunteerSignupEntity(
                id = "vol_03",
                eventId = "evt_01",
                eventTitle = "Sunday Worship Celebration",
                roleName = "Children's Sunday School Helper",
                description = "Assist lead teachers with toddler arts, snacks, and escorting children safely to parents.",
                neededSlots = 8,
                filledSlots = 5,
                userSignedUp = false,
                timeSlot = "9:45 AM - 11:30 AM"
            ),
            VolunteerSignupEntity(
                id = "vol_04",
                eventId = "evt_03",
                eventTitle = "City Outreach & Food Bank",
                roleName = "Pantry Distribution & Cart Loader",
                description = "Pack food boxes with fresh produce, canned goods, and load vehicles at curbside.",
                neededSlots = 15,
                filledSlots = 9,
                userSignedUp = true,
                timeSlot = "7:45 AM - 12:30 PM"
            ),
            VolunteerSignupEntity(
                id = "vol_05",
                eventId = "evt_02",
                eventTitle = "Midweek Community Dinner",
                roleName = "Hospitality & Dinner Server",
                description = "Set up dining hall tables, serve hot buffet meals, and oversee fellowship cleanup.",
                neededSlots = 5,
                filledSlots = 3,
                userSignedUp = false,
                timeSlot = "5:45 PM - 8:45 PM"
            )
        )
        churchDao.insertVolunteerRoles(initialVolunteers)

        val initialAnnouncements = listOf(
            AnnouncementEntity(
                id = "ann_01",
                title = "URGENT PRAYER: Pastor Bennett's Father Hospitalized",
                content = "Dear church family, please join in fervent prayer for Elder Robert Bennett who was admitted this morning with respiratory complications. We are believing the Lord for complete healing and peace for the family.",
                author = "Deacon Board",
                authorRole = "Pastoral Care",
                category = "Urgent Prayer",
                isUrgent = true,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 45 // 45m ago
            ),
            AnnouncementEntity(
                id = "ann_02",
                title = "Schedule Change: Wednesday Night Bible Study Shift",
                content = "Due to the civic road resurfacing on Cornerstone Blvd, this Wednesday's fellowship dinner will begin 30 minutes earlier at 6:00 PM. North parking gate will be open for easy access.",
                author = "Church Administration",
                authorRole = "Operations Director",
                category = "Schedule Change",
                isUrgent = true,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 180 // 3h ago
            ),
            AnnouncementEntity(
                id = "ann_03",
                title = "Fall Baptisms Celebration at Lakeside Park",
                content = "We have 14 candidates rejoicing to be baptized next Sunday afternoon at 2:00 PM! Bring your lawn chairs, picnic baskets, and invite friends and family for an afternoon of praise.",
                author = "Pastor Eleanor Bennett",
                authorRole = "Associate Pastor",
                category = "General Announcement",
                isUrgent = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 // 1 day ago
            ),
            AnnouncementEntity(
                id = "ann_04",
                title = "Sunday School Curriculum Winter Term Launch",
                content = "New quarterly curriculum workbooks for Toddlers through High School are available for pickup at the Ministry Resource Desk. Parents can view the syllabus directly in the app.",
                author = "Sister Grace Miller",
                authorRole = "Sunday School Superintendent",
                category = "Ministry Spotlight",
                isUrgent = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 48 // 2 days ago
            )
        )
        churchDao.insertAnnouncements(initialAnnouncements)

        val initialRecaps = listOf(
            MediaRecapEntity(
                id = "rcp_01",
                title = "Parish Thanksgiving Harvest Banquet 2026",
                description = "Over 350 congregants gathered for an evening of testimonies, table fellowship, and children's bell choir presentation.",
                mediaType = "Photo",
                mediaUrl = "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800",
                eventName = "Annual Harvest Gathering",
                date = "Last Sunday",
                author = "Media Ministry Team",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 72
            ),
            MediaRecapEntity(
                id = "rcp_02",
                title = "Ignite Youth Camp 'Unshakeable' Highlights",
                description = "Recap video capturing 62 teens dedicating their lives to Christ by the campfire, worship beneath the stars, and team relay games.",
                mediaType = "Video Recap",
                mediaUrl = "https://images.unsplash.com/photo-1523580494863-6f3031224c94?w=800",
                eventName = "Summer Youth Retreat",
                date = "2 Weeks Ago",
                author = "Youth Pastor Mark",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 160
            ),
            MediaRecapEntity(
                id = "rcp_03",
                title = "Downtown Community Food Pantry Blessing",
                description = "Volunteers distributing warm jackets, family meal kits, and praying individually with our neighborhood residents.",
                mediaType = "Photo",
                mediaUrl = "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?w=800",
                eventName = "Mercy Ministry Outreach",
                date = "3 Weeks Ago",
                author = "Sister Chloe Davis",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 240
            )
        )
        churchDao.insertMediaRecaps(initialRecaps)
    }

    fun getAllEvents(): Flow<List<ChurchEvent>> {
        return churchDao.getAllEvents().map { list ->
            list.map { entity ->
                ChurchEvent(
                    id = entity.id,
                    title = entity.title,
                    date = entity.date,
                    time = entity.time,
                    location = entity.location,
                    description = entity.description,
                    category = entity.category,
                    rsvpCount = entity.rsvpCount,
                    userRsvpStatus = entity.userRsvpStatus
                )
            }
        }
    }

    suspend fun setRsvpStatus(eventId: String, currentStatus: String, newStatus: String) {
        val delta = when {
            newStatus == "Attending" && currentStatus != "Attending" -> 1
            newStatus != "Attending" && currentStatus == "Attending" -> -1
            else -> 0
        }
        churchDao.updateRsvp(eventId, newStatus, delta)
    }

    fun getAllVolunteerRoles(): Flow<List<VolunteerRole>> {
        return churchDao.getAllVolunteerRoles().map { list ->
            list.map { entity ->
                VolunteerRole(
                    id = entity.id,
                    eventId = entity.eventId,
                    eventTitle = entity.eventTitle,
                    roleName = entity.roleName,
                    description = entity.description,
                    neededSlots = entity.neededSlots,
                    filledSlots = entity.filledSlots,
                    userSignedUp = entity.userSignedUp,
                    timeSlot = entity.timeSlot
                )
            }
        }
    }

    suspend fun toggleVolunteerRole(roleId: String, currentSignedUp: Boolean) {
        val newSignedUp = !currentSignedUp
        val delta = if (newSignedUp) 1 else -1
        churchDao.toggleVolunteerSignup(roleId, newSignedUp, delta)
    }

    fun getAllAnnouncements(): Flow<List<ChurchAnnouncement>> {
        return churchDao.getAllAnnouncements().map { list ->
            list.map { entity ->
                ChurchAnnouncement(
                    id = entity.id,
                    title = entity.title,
                    content = entity.content,
                    author = entity.author,
                    authorRole = entity.authorRole,
                    category = entity.category,
                    isUrgent = entity.isUrgent,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    suspend fun addAnnouncement(
        title: String,
        content: String,
        author: String,
        authorRole: String,
        category: String,
        isUrgent: Boolean
    ) {
        churchDao.insertAnnouncement(
            AnnouncementEntity(
                id = "ann_" + UUID.randomUUID().toString().take(8),
                title = title,
                content = content,
                author = author,
                authorRole = authorRole,
                category = category,
                isUrgent = isUrgent,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun getAllMediaRecaps(): Flow<List<MediaRecap>> {
        return churchDao.getAllMediaRecaps().map { list ->
            list.map { entity ->
                MediaRecap(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    mediaType = entity.mediaType,
                    mediaUrl = entity.mediaUrl,
                    eventName = entity.eventName,
                    date = entity.date,
                    author = entity.author,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    suspend fun addMediaRecap(
        title: String,
        description: String,
        mediaType: String,
        mediaUrl: String,
        eventName: String,
        author: String
    ) {
        churchDao.insertMediaRecap(
            MediaRecapEntity(
                id = "rcp_" + UUID.randomUUID().toString().take(8),
                title = title,
                description = description,
                mediaType = mediaType,
                mediaUrl = mediaUrl.ifBlank { "https://images.unsplash.com/photo-1544427920-c49ccfb85579?w=800" },
                eventName = eventName,
                date = "Just now",
                author = author,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}
