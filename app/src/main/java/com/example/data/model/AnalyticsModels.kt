package com.example.data.model

data class MinistryStat(
    val ministryName: String,
    val activeParticipants: Int,
    val growthPercentage: Int, // e.g. +14%
    val volunteerFillRate: Float, // e.g. 0.88f (88%)
    val targetGoal: String
)

data class WeeklyAttendancePoint(
    val weekLabel: String,
    val count: Int,
    val isHighlight: Boolean = false
)

data class MinistryEngagementSummary(
    val totalActiveMembers: Int = 428,
    val weeklyAverageAttendance: Int = 340,
    val activeStudyGroups: Int = 18,
    val volunteerSignupsThisMonth: Int = 64,
    val sundaySchoolEnrollment: Int = 112,
    val prayerRequestsAnswered: Int = 37,
    val givingPledgeProgress: Float = 0.84f // 84%
)
