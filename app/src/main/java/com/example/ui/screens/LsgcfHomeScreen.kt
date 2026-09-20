package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfDarkPill
import com.example.ui.theme.LsgcfDivider
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LsgcfHomeScreen(
    onOpenDrawer: () -> Unit,
    onNavigateToSermons: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToDoctrine: () -> Unit,
    onNavigateToSundaySchool: () -> Unit,
    onNavigateTo12Lessons: () -> Unit,
    onNavigateToStatementOfFaith: () -> Unit,
    onNavigateToQuizzes: () -> Unit,
    onOpenNotifications: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isVerseExpanded by remember { mutableStateOf(false) }

    // Today's date matching screenshot style: "TUESDAY", "September 15, 2026"
    val dayOfWeek = remember { SimpleDateFormat("EEEE", Locale.US).format(Date()).uppercase() }
    val formattedDate = remember { SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Date()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_home_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.testTag("home_hamburger_menu")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = LsgcfTextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "LSGCF",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = LsgcfTextPrimary,
                    letterSpacing = 1.sp
                )

                Box {
                    IconButton(
                        onClick = onOpenNotifications,
                        modifier = Modifier.testTag("home_notifications_bell")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = LsgcfTextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Gold notification badge dot
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp, end = 12.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(LsgcfGold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date Header
            Text(
                text = dayOfWeek,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = LsgcfTextMuted,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = formattedDate,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = LsgcfTextPrimary,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // "VERSE OF THE DAY" Pill Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(LsgcfDarkPill)
                    .clickable { isVerseExpanded = !isVerseExpanded }
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .animateContentSize()
                    .testTag("verse_of_the_day_card")
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "VERSE OF THE DAY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LsgcfGold,
                            letterSpacing = 1.5.sp
                        )

                        Text(
                            text = if (isVerseExpanded) "Show Less" else "Tap to Read",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }

                    AnimatedVisibility(visible = isVerseExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        ) {
                            Text(
                                text = "\"For to me, to live is Christ, and to die is gain.\"",
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                fontSize = 16.sp,
                                color = Color.White,
                                lineHeight = 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Philippians 1:21",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LsgcfGold
                                )

                                Row {
                                    IconButton(
                                        onClick = {
                                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_TEXT, "Philippians 1:21 - For to me, to live is Christ, and to die is gain. (LSGCF)")
                                            }
                                            context.startActivity(Intent.createChooser(shareIntent, "Share Verse"))
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share",
                                            tint = Color.White.copy(alpha = 0.8f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Verse bookmarked to your study journal!")
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = "Bookmark",
                                            tint = LsgcfGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Action Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Card 1: Sermon Videos
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToSermons() }
                        .testTag("action_sermon_videos"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(LsgcfBg)
                                .border(1.dp, LsgcfCardBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = LsgcfTextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Sermon Videos",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = LsgcfTextPrimary
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Latest message",
                            fontSize = 12.sp,
                            color = LsgcfTextMuted
                        )
                    }
                }

                // Card 2: Church Activity
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToEvents() }
                        .testTag("action_church_activity"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(LsgcfBg)
                                .border(1.dp, LsgcfCardBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarToday,
                                contentDescription = "Calendar",
                                tint = LsgcfTextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Church Activity",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = LsgcfTextPrimary
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "This week",
                            fontSize = 12.sp,
                            color = LsgcfTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // RESOURCES Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RESOURCES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LsgcfTextCategory,
                    letterSpacing = 1.2.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                HorizontalDivider(
                    color = LsgcfCardBorder,
                    thickness = 0.8.dp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Grouped Resources White Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("resources_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ResourceItemRow(
                        title = "Doctrine",
                        categoryLabel = "THEOLOGY",
                        onClick = onNavigateToDoctrine
                    )

                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    ResourceItemRow(
                        title = "Sunday School",
                        categoryLabel = "EDUCATION",
                        onClick = onNavigateToSundaySchool
                    )

                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    ResourceItemRow(
                        title = "12 Lessons",
                        categoryLabel = "DISCIPLESHIP",
                        onClick = onNavigateTo12Lessons
                    )

                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    ResourceItemRow(
                        title = "Statement of Faith",
                        categoryLabel = "BELIEFS",
                        onClick = onNavigateToStatementOfFaith
                    )

                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    ResourceItemRow(
                        title = "Quizzes",
                        categoryLabel = "KNOWLEDGE",
                        onClick = onNavigateToQuizzes
                    )

                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    ResourceItemRow(
                        title = "Church Activity",
                        categoryLabel = "EVENTS",
                        onClick = onNavigateToEvents
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ResourceItemRow(
    title: String,
    categoryLabel: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = LsgcfTextPrimary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = LsgcfTextCategory,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = LsgcfTextCategory,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
