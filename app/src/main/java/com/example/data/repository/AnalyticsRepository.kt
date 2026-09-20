package com.example.data.repository

import com.example.data.model.MinistryEngagementSummary
import com.example.data.model.MinistryStat
import com.example.data.model.WeeklyAttendancePoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AnalyticsRepository {

    fun getMinistryEngagementSummary(): Flow<MinistryEngagementSummary> {
        return flowOf(
            MinistryEngagementSummary(
                totalActiveMembers = 452,
                weeklyAverageAttendance = 368,
                activeStudyGroups = 19,
                volunteerSignupsThisMonth = 72,
                sundaySchoolEnrollment = 118,
                prayerRequestsAnswered = 41,
                givingPledgeProgress = 0.88f
            )
        )
    }

    fun getWeeklyAttendanceTrend(): Flow<List<WeeklyAttendancePoint>> {
        return flowOf(
            listOf(
                WeeklyAttendancePoint("Aug 16", 320),
                WeeklyAttendancePoint("Aug 23", 335),
                WeeklyAttendancePoint("Aug 30", 310),
                WeeklyAttendancePoint("Sep 06", 355),
                WeeklyAttendancePoint("Sep 13", 370),
                WeeklyAttendancePoint("Today", 392, isHighlight = true)
            )
        )
    }

    fun getMinistryBreakdownStats(): Flow<List<MinistryStat>> {
        return flowOf(
            listOf(
                MinistryStat(
                    ministryName = "Sunday Worship & Music",
                    activeParticipants = 160,
                    growthPercentage = 12,
                    volunteerFillRate = 0.92f,
                    targetGoal = "Goal: 175 by Q4"
                ),
                MinistryStat(
                    ministryName = "Sunday School & Kids Ministry",
                    activeParticipants = 118,
                    growthPercentage = 18,
                    volunteerFillRate = 0.86f,
                    targetGoal = "Goal: 130 by Q4"
                ),
                MinistryStat(
                    ministryName = "Small Groups & Bible Study",
                    activeParticipants = 210,
                    growthPercentage = 24,
                    volunteerFillRate = 0.95f,
                    targetGoal = "Goal: 250 by Q4"
                ),
                MinistryStat(
                    ministryName = "Community Food Pantry & Mercy",
                    activeParticipants = 85,
                    growthPercentage = 31,
                    volunteerFillRate = 0.78f,
                    targetGoal = "Outreach Goal: 100 Volunteers"
                ),
                MinistryStat(
                    ministryName = "Ignite Youth & Young Adults",
                    activeParticipants = 76,
                    growthPercentage = 15,
                    volunteerFillRate = 0.80f,
                    targetGoal = "Goal: 90 by Q4"
                )
            )
        )
    }
}
