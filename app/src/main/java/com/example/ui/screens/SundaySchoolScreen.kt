package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AttendanceStatus
import com.example.data.model.SundaySchoolLesson
import com.example.data.repository.SundaySchoolRepository
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SacredCrimson
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@Composable
fun SundaySchoolScreen(
    sundaySchoolRepository: SundaySchoolRepository,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val classes = sundaySchoolRepository.classes
    var selectedClassIndex by remember { mutableIntStateOf(1) } // Default to Primary
    val currentClass = classes[selectedClassIndex]

    val attendanceRecords by sundaySchoolRepository.getAttendanceForClass(currentClass.id)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val lessons = sundaySchoolRepository.curriculumLessons[currentClass.id] ?: emptyList()
    var subTab by remember { mutableIntStateOf(0) } // 0 = Curriculum & Lessons, 1 = Automated Attendance Logging

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("sunday_school_screen")
    ) {
        // Class Age Group selector
        ScrollableTabRow(
            selectedTabIndex = selectedClassIndex,
            edgePadding = 16.dp,
            containerColor = DeepNavy,
            contentColor = Color.White
        ) {
            classes.forEachIndexed { index, cl ->
                val isSelected = selectedClassIndex == index
                Tab(
                    selected = isSelected,
                    onClick = { selectedClassIndex = index },
                    modifier = Modifier.testTag("ss_class_tab_${cl.id}")
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = cl.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) HolyGold else Color.White
                        )
                        Text(
                            text = cl.ageGroup,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) SoftGold else Color.LightGray.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Subtabs: Curriculum vs Attendance
        TabRow(
            selectedTabIndex = subTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = DeepNavy
        ) {
            Tab(
                selected = subTab == 0,
                onClick = { subTab = 0 },
                modifier = Modifier.testTag("curriculum_tab")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp), tint = DeepNavy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Curriculum & Lessons",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (subTab == 0) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            Tab(
                selected = subTab == 1,
                onClick = { subTab = 1 },
                modifier = Modifier.testTag("attendance_tab")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp), tint = DeepNavy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Attendance Logging (${attendanceRecords.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (subTab == 1) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Active Class Summary Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = DeepNavy.copy(alpha = 0.05f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${currentClass.name} • ${currentClass.room}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "Teacher: ${currentClass.teacherName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HolyGold.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "${currentClass.studentCount} Students",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        if (subTab == 0) {
            // Curriculum and Lessons
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(lessons) { lesson ->
                    LessonCard(lesson = lesson)
                }
            }
        } else {
            // Automated Attendance Logging
            val presentCount = attendanceRecords.count { it.status == AttendanceStatus.PRESENT }
            val rate = if (attendanceRecords.isNotEmpty()) {
                (presentCount.toFloat() / attendanceRecords.size.toFloat())
            } else 0f

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Quick Attendance Stats
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Today's Attendance Roll-Call",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                            Text(
                                text = "$presentCount of ${attendanceRecords.size} Present (${(rate * 100).toInt()}%)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = OliveSage
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { rate },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = OliveSage,
                            trackColor = Color.LightGray.copy(alpha = 0.3f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(attendanceRecords) { record ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("student_row_${record.id}"),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = record.studentName,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy
                                    )
                                    if (record.note.isNotBlank()) {
                                        Text(
                                            text = record.note,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    // Present Button
                                    val isPresent = record.status == AttendanceStatus.PRESENT
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                sundaySchoolRepository.updateAttendanceStatus(
                                                    recordId = record.id,
                                                    classId = record.classId,
                                                    studentName = record.studentName,
                                                    newStatus = AttendanceStatus.PRESENT,
                                                    note = "Verified in room"
                                                )
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isPresent) OliveSage else Color.LightGray.copy(alpha = 0.3f),
                                            contentColor = if (isPresent) Color.White else Color.DarkGray
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.testTag("present_${record.id}")
                                    ) {
                                        Text("Present", style = MaterialTheme.typography.labelSmall)
                                    }

                                    // Absent Button
                                    val isAbsent = record.status == AttendanceStatus.ABSENT
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                sundaySchoolRepository.updateAttendanceStatus(
                                                    recordId = record.id,
                                                    classId = record.classId,
                                                    studentName = record.studentName,
                                                    newStatus = AttendanceStatus.ABSENT,
                                                    note = "Absent"
                                                )
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isAbsent) SacredCrimson else Color.LightGray.copy(alpha = 0.3f),
                                            contentColor = if (isAbsent) Color.White else Color.DarkGray
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.testTag("absent_${record.id}")
                                    ) {
                                        Text("Absent", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: SundaySchoolLesson) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("lesson_${lesson.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = HolyGold.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "Week ${lesson.weekNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = DeepNavy,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = if (lesson.isCompleted) "✓ Completed" else "In Progress",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (lesson.isCompleted) OliveSage else HolyGold,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DeepNavy
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Scripture: ${lesson.scriptureRef}",
                style = MaterialTheme.typography.labelSmall,
                color = HolyGold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = DeepNavy.copy(alpha = 0.05f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Memory Verse:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "\"${lesson.memoryVerse}\"",
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lesson.lessonSummary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Teacher Activity: ${lesson.teacherNotes}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
