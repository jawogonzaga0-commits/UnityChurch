package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.LsgcfRepository
import com.example.ui.theme.HighlightEmerald
import com.example.ui.theme.HighlightRose
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfGoldLight
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

@Composable
fun LsgcfQuizzesScreen(
    lsgcfRepository: LsgcfRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = remember { lsgcfRepository.quizQuestions }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() }

    val answeredCount = userAnswers.size
    val correctCount = userAnswers.count { (qId, selectedIdx) ->
        val q = questions.find { it.id == qId }
        q?.correctIndex == selectedIdx
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_quizzes_screen")
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                            text = "Bible Knowledge Quiz",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = LsgcfTextPrimary
                        )
                    }
                }

                if (userAnswers.isNotEmpty()) {
                    IconButton(onClick = { userAnswers.clear() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = LsgcfGold)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Score card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Current Score",
                                    fontSize = 12.sp,
                                    color = LsgcfTextMuted
                                )
                                Text(
                                    text = "$correctCount / ${questions.size}",
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = LsgcfGold
                                )
                            }

                            Text(
                                text = if (answeredCount == questions.size) "Completed! 🎉" else "$answeredCount of ${questions.size} answered",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = LsgcfTextPrimary
                            )
                        }
                    }
                }

                itemsIndexed(questions) { qIndex, question ->
                    val selectedAnswer = userAnswers[question.id]
                    val isAnswered = selectedAnswer != null

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
                            Text(
                                text = "QUESTION ${qIndex + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = LsgcfGold,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = question.question,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = LsgcfTextPrimary,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Options list
                            question.options.forEachIndexed { optIndex, optionText ->
                                val isSelected = selectedAnswer == optIndex
                                val isCorrectOption = question.correctIndex == optIndex

                                val optionBorderColor = when {
                                    !isAnswered -> LsgcfCardBorder
                                    isCorrectOption -> HighlightEmerald
                                    isSelected -> HighlightRose
                                    else -> LsgcfCardBorder
                                }

                                val optionBg = when {
                                    !isAnswered -> if (isSelected) LsgcfGoldLight else Color.Transparent
                                    isCorrectOption -> HighlightEmerald.copy(alpha = 0.25f)
                                    isSelected -> HighlightRose.copy(alpha = 0.25f)
                                    else -> Color.Transparent
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(optionBg)
                                        .border(1.dp, optionBorderColor, RoundedCornerShape(10.dp))
                                        .clickable(enabled = !isAnswered) {
                                            userAnswers[question.id] = optIndex
                                        }
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = optionText,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected || (isAnswered && isCorrectOption)) FontWeight.Bold else FontWeight.Normal,
                                        color = LsgcfTextPrimary,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (isAnswered) {
                                        if (isCorrectOption) {
                                            Icon(Icons.Default.Check, contentDescription = "Correct", tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                                        } else if (isSelected) {
                                            Icon(Icons.Default.Close, contentDescription = "Wrong", tint = Color(0xFFC62828), modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }

                            // Explanation if answered
                            AnimatedVisibility(visible = isAnswered) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(LsgcfBg)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = "EXPLANATION (${question.reference})",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LsgcfGold,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = question.explanation,
                                        fontSize = 12.sp,
                                        color = LsgcfTextMuted,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}
