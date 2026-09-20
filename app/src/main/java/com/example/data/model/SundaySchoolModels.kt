package com.example.data.model

data class SundaySchoolClass(
    val id: String,
    val name: String,
    val ageGroup: String, // "Toddlers (2-4)", "Primary (5-8)", "Juniors (9-12)", "Youth (13-17)", "Adult Forum"
    val room: String,
    val teacherName: String,
    val currentLessonTitle: String,
    val studentCount: Int
)

data class SundaySchoolLesson(
    val id: String,
    val classId: String,
    val title: String,
    val weekNumber: Int,
    val scriptureRef: String,
    val memoryVerse: String,
    val lessonSummary: String,
    val teacherNotes: String,
    val isCompleted: Boolean = false
)

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    EXCUSED
}

data class StudentAttendance(
    val id: String,
    val classId: String,
    val studentName: String,
    val date: String,
    val status: AttendanceStatus,
    val note: String = ""
)
