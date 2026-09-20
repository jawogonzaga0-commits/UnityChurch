package com.example.data.repository

import com.example.data.local.SundaySchoolAttendanceEntity
import com.example.data.local.SundaySchoolDao
import com.example.data.model.AttendanceStatus
import com.example.data.model.StudentAttendance
import com.example.data.model.SundaySchoolClass
import com.example.data.model.SundaySchoolLesson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SundaySchoolRepository(private val sundaySchoolDao: SundaySchoolDao) {

    val classes: List<SundaySchoolClass> = listOf(
        SundaySchoolClass(
            id = "cls_toddlers",
            name = "Little Lambs",
            ageGroup = "Toddlers (Ages 2-4)",
            room = "Room 101 - Joy Wing",
            teacherName = "Sister Rachel Kim & Brother Luke",
            currentLessonTitle = "God Made the Sun, Moon, and Animals",
            studentCount = 8
        ),
        SundaySchoolClass(
            id = "cls_primary",
            name = "Kingdom Explorers",
            ageGroup = "Primary (Ages 5-8)",
            room = "Room 104 - Promise Wing",
            teacherName = "Sister Grace Miller",
            currentLessonTitle = "David, Goliath, and Courage in God",
            studentCount = 12
        ),
        SundaySchoolClass(
            id = "cls_juniors",
            name = "Faith Champions",
            ageGroup = "Juniors (Ages 9-12)",
            room = "Room 202 - Upper Hall",
            teacherName = "Brother Marcus Vance",
            currentLessonTitle = "The Miracles of Jesus in Galilee",
            studentCount = 14
        ),
        SundaySchoolClass(
            id = "cls_youth",
            name = "Ignite Youth Discipleship",
            ageGroup = "Youth (Ages 13-17)",
            room = "Youth Pavilion Studio",
            teacherName = "Youth Pastor Mark & Hannah",
            currentLessonTitle = "Living Counter-Cultural: The Beatitudes",
            studentCount = 18
        ),
        SundaySchoolClass(
            id = "cls_adults",
            name = "Berean Adult Scripture Forum",
            ageGroup = "Adults & College",
            room = "Sanctuary Annex B",
            teacherName = "Dr. Thomas Wright",
            currentLessonTitle = "Justification by Faith in Romans",
            studentCount = 32
        )
    )

    val curriculumLessons: Map<String, List<SundaySchoolLesson>> = mapOf(
        "cls_toddlers" to listOf(
            SundaySchoolLesson(
                id = "lsn_t1",
                classId = "cls_toddlers",
                title = "Creation Wonders: God Made the Animals",
                weekNumber = 1,
                scriptureRef = "Genesis 1:20-25",
                memoryVerse = "Genesis 1:1 — In the beginning God created the heaven and the earth.",
                lessonSummary = "Exploring God's creativity through tactile animal plushies and songs.",
                teacherNotes = "Distribute coloring craft at 10:15 AM. Sing 'He's Got the Whole World in His Hands'.",
                isCompleted = true
            ),
            SundaySchoolLesson(
                id = "lsn_t2",
                classId = "cls_toddlers",
                title = "Noah's Ark & the Rainbow Promise",
                weekNumber = 2,
                scriptureRef = "Genesis 9:12-17",
                memoryVerse = "Psalm 136:1 — O give thanks unto the Lord; for he is good.",
                lessonSummary = "Teaching God's faithfulness and caring protection over every creature.",
                teacherNotes = "Rainbow ribbon crafts and fruit slice snack table.",
                isCompleted = false
            )
        ),
        "cls_primary" to listOf(
            SundaySchoolLesson(
                id = "lsn_p1",
                classId = "cls_primary",
                title = "David and Goliath: Trusting God's Power",
                weekNumber = 1,
                scriptureRef = "1 Samuel 17:32-50",
                memoryVerse = "Psalm 27:1 — The Lord is my light and my salvation; whom shall I fear?",
                lessonSummary = "How faith in God overcomes the largest giants in life.",
                teacherNotes = "Smooth stone craft activity and interactive skit rehearsal.",
                isCompleted = true
            ),
            SundaySchoolLesson(
                id = "lsn_p2",
                classId = "cls_primary",
                title = "Daniel in the Lions' Den",
                weekNumber = 2,
                scriptureRef = "Daniel 6:10-23",
                memoryVerse = "Proverbs 3:5 — Trust in the Lord with all thine heart.",
                lessonSummary = "Standing firm in daily prayer even when faced with opposition.",
                teacherNotes = "Paper lion masks and small group prayer circles.",
                isCompleted = false
            )
        ),
        "cls_juniors" to listOf(
            SundaySchoolLesson(
                id = "lsn_j1",
                classId = "cls_juniors",
                title = "Feeding the 5,000: Little Becomes Much",
                weekNumber = 1,
                scriptureRef = "John 6:1-14",
                memoryVerse = "Philippians 4:19 — But my God shall supply all your need according to his riches.",
                lessonSummary = "Surrendering what we have into Jesus' hands for miraculous multiplication.",
                teacherNotes = "Group calculation activity: how much did Jesus multiply? Bread roll sharing.",
                isCompleted = true
            ),
            SundaySchoolLesson(
                id = "lsn_j2",
                classId = "cls_juniors",
                title = "Walking on Water: Keeping Your Eyes on Jesus",
                weekNumber = 2,
                scriptureRef = "Matthew 14:22-33",
                memoryVerse = "Hebrews 12:2 — Looking unto Jesus the author and finisher of our faith.",
                lessonSummary = "Overcoming storms of doubt, fear, and peer pressure.",
                teacherNotes = "Discussion prompt: When did you feel like sinking this week?",
                isCompleted = false
            )
        ),
        "cls_youth" to listOf(
            SundaySchoolLesson(
                id = "lsn_y1",
                classId = "cls_youth",
                title = "The Sermon on the Mount: The Counter-Cultural Life",
                weekNumber = 1,
                scriptureRef = "Matthew 5:1-16",
                memoryVerse = "Matthew 5:14 — Ye are the light of the world. A city that is set on an hill cannot be hid.",
                lessonSummary = "Dismantling modern status culture with kingdom humility, purity, and peacemaking.",
                teacherNotes = "Case studies on social media pressure and standing up for truth with gentleness.",
                isCompleted = true
            ),
            SundaySchoolLesson(
                id = "lsn_y2",
                classId = "cls_youth",
                title = "Armor of God: Standing Strong in Spiritual Warfare",
                weekNumber = 2,
                scriptureRef = "Ephesians 6:10-18",
                memoryVerse = "Ephesians 6:10 — Finally, my brethren, be strong in the Lord, and in the power of his might.",
                lessonSummary = "Equipping young minds against cultural deception with the belt of truth and shield of faith.",
                teacherNotes = "Interactive worksheet on each piece of Roman soldier armor.",
                isCompleted = false
            )
        ),
        "cls_adults" to listOf(
            SundaySchoolLesson(
                id = "lsn_a1",
                classId = "cls_adults",
                title = "Romans 8: The Triumph of the Holy Spirit",
                weekNumber = 1,
                scriptureRef = "Romans 8:1-39",
                memoryVerse = "Romans 8:31 — If God be for us, who can be against us?",
                lessonSummary = "Deep expository study on assurance of salvation, adoption, and the love of God in Christ.",
                teacherNotes = "Theological handout: Sanctification vs Justification Greek lexicons.",
                isCompleted = true
            )
        )
    )

    private val initialRosters: Map<String, List<String>> = mapOf(
        "cls_toddlers" to listOf("Noah Vance", "Emma Sullivan", "Liam Cooper", "Mia Perez", "Lucas Miller", "Chloe Jenkins"),
        "cls_primary" to listOf("Ethan Baker", "Sophia Ramirez", "Benjamin Cole", "Ava Brooks", "Jackson Hall", "Harper Lee", "Samuel White"),
        "cls_juniors" to listOf("Daniel Foster", "Hannah Kim", "Caleb Watson", "Grace Mitchell", "Joshua Ross", "Zoe Henderson", "Isaac Turner"),
        "cls_youth" to listOf("Gabriel Bennett", "Abigail Turner", "Nathan Powell", "Emily Wright", "Matthew Scott", "Sarah Collins", "Elijah Adams"),
        "cls_adults" to listOf("Robert Martinez", "Evelyn Reed", "Jameson Price", "Deborah Long", "Victor Hernandez", "Angela Young")
    )

    suspend fun seedAttendanceIfEmpty(classId: String) {
        val students = initialRosters[classId] ?: listOf("Student A", "Student B", "Student C")
        val today = "Sunday 10:15 AM"
        val attendanceList = students.mapIndexed { index, name ->
            val status = if (index % 5 == 4) "ABSENT" else "PRESENT"
            SundaySchoolAttendanceEntity(
                id = "${classId}_${name.replace(" ", "_")}",
                classId = classId,
                studentName = name,
                date = today,
                status = status,
                note = if (status == "ABSENT") "Family on weekend trip" else "Memorized memory verse"
            )
        }
        sundaySchoolDao.insertAttendanceList(attendanceList)
    }

    fun getAttendanceForClass(classId: String): Flow<List<StudentAttendance>> {
        return sundaySchoolDao.getAttendanceForClass(classId).map { list ->
            list.map { entity ->
                StudentAttendance(
                    id = entity.id,
                    classId = entity.classId,
                    studentName = entity.studentName,
                    date = entity.date,
                    status = try {
                        AttendanceStatus.valueOf(entity.status)
                    } catch (_: Exception) {
                        AttendanceStatus.PRESENT
                    },
                    note = entity.note
                )
            }
        }
    }

    suspend fun updateAttendanceStatus(recordId: String, classId: String, studentName: String, newStatus: AttendanceStatus, note: String) {
        sundaySchoolDao.updateSingleAttendance(
            SundaySchoolAttendanceEntity(
                id = recordId,
                classId = classId,
                studentName = studentName,
                date = "Sunday 10:15 AM",
                status = newStatus.name,
                note = note
            )
        )
    }
}
