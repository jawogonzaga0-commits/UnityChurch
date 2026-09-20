package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.DiscipleshipLesson
import com.example.data.repository.LsgcfRepository
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfDivider
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfGoldLight
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

@Composable
fun LsgcfDiscipleshipScreen(
    lsgcfRepository: LsgcfRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lessons by lsgcfRepository.lessons.collectAsStateWithLifecycle()
    val completedCount = lessons.count { it.isCompleted }
    val progress = if (lessons.isNotEmpty()) completedCount.toFloat() / lessons.size.toFloat() else 0f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_discipleship_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LsgcfTextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "LSGCF",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "12 Lessons Discipleship",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = LsgcfTextPrimary
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Progress Overview Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Your Discipleship Journey",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = LsgcfTextPrimary
                                )
                                Text(
                                    text = "$completedCount of 12 Completed",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = LsgcfGold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(CircleShape),
                                color = LsgcfGold,
                                trackColor = LsgcfBg
                            )
                        }
                    }
                }

                items(lessons) { lesson ->
                    LessonCard(
                        lesson = lesson,
                        onToggleCompleted = {
                            lsgcfRepository.toggleLessonCompleted(lesson.number)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}

@Composable
private fun LessonCard(
    lesson: DiscipleshipLesson,
    onToggleCompleted: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(if (lesson.isCompleted) LsgcfGold else LsgcfBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${lesson.number}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (lesson.isCompleted) Color.White else LsgcfTextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = lesson.title,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = LsgcfTextPrimary
                        )
                        Text(
                            text = lesson.scripturePassage,
                            fontSize = 12.sp,
                            color = LsgcfTextMuted
                        )
                    }
                }

                IconButton(onClick = onToggleCompleted) {
                    Icon(
                        imageVector = if (lesson.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                        contentDescription = "Toggle Complete",
                        tint = if (lesson.isCompleted) LsgcfGold else LsgcfTextCategory,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = lesson.summary,
                fontSize = 13.sp,
                color = LsgcfTextPrimary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Hide Study Materials" else "View Memory Verse & Questions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LsgcfGold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = LsgcfGold,
                    modifier = Modifier.size(18.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Memory Verse Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(LsgcfGoldLight)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "MEMORY VERSE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = LsgcfGold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = lesson.memoryVerse,
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp,
                                color = LsgcfTextPrimary,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CORE TAKEAWAYS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    lesson.keyTakeaways.forEach { point ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("• ", fontWeight = FontWeight.Bold, color = LsgcfGold, fontSize = 12.sp)
                            Text(point, fontSize = 12.sp, color = LsgcfTextMuted, lineHeight = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "REFLECTION QUESTION",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = lesson.reflectionQuestion,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = LsgcfTextPrimary
                    )
                }
            }
        }
    }
}
