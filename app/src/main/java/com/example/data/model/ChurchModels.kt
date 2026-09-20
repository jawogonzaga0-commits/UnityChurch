package com.example.data.model

data class ChurchProfile(
    val name: String,
    val denomination: String,
    val seniorPastor: String,
    val address: String,
    val phone: String,
    val email: String,
    val website: String,
    val serviceTimes: List<String>,
    val missionStatement: String,
    val establishedYear: String
)

data class ChurchEvent(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val category: String, // "Worship Service", "Community Outreach", "Prayer Vigil", "Fellowship", "Youth"
    val rsvpCount: Int = 0,
    val userRsvpStatus: String = "None" // "Attending", "Interested", "None"
)

data class VolunteerRole(
    val id: String,
    val eventId: String,
    val eventTitle: String,
    val roleName: String,
    val description: String,
    val neededSlots: Int,
    val filledSlots: Int,
    val userSignedUp: Boolean = false,
    val timeSlot: String = "Sunday 9:00 AM - 12:30 PM"
)

data class ChurchAnnouncement(
    val id: String,
    val title: String,
    val content: String,
    val author: String,
    val authorRole: String,
    val category: String, // "Urgent Prayer", "Schedule Change", "General Announcement", "Ministry Spotlight"
    val isUrgent: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class MediaRecap(
    val id: String,
    val title: String,
    val description: String,
    val mediaType: String, // "Photo", "Video Recap", "Testimony"
    val mediaUrl: String,
    val eventName: String,
    val date: String,
    val author: String,
    val timestamp: Long = System.currentTimeMillis()
)
