package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SermonVideo
import com.example.data.repository.LsgcfRepository
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

@Composable
fun LsgcfSermonVideosScreen(
    lsgcfRepository: LsgcfRepository,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val sermons = remember { lsgcfRepository.sermons }
    var activePlayingSermon by remember { mutableStateOf<SermonVideo?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_sermon_videos_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = LsgcfTextPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Column {
                    Text(
                        text = "LSGCF",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Sermon Videos",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = LsgcfTextPrimary
                    )
                }
            }

            // List of Sermon Cards
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(sermons) { sermon ->
                    SermonVideoCard(
                        sermon = sermon,
                        onClick = { activePlayingSermon = sermon }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Interactive Video Player Dialog
        activePlayingSermon?.let { sermon ->
            SermonPlayerDialog(
                sermon = sermon,
                onDismiss = { activePlayingSermon = null }
            )
        }
    }
}

@Composable
fun SermonVideoCard(
    sermon: SermonVideo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("sermon_card_${sermon.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Thumbnail with gradient and play button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(sermon.videoGradientColors.getOrElse(0) { 0xFF2E1A10 }),
                                Color(sermon.videoGradientColors.getOrElse(1) { 0xFF8A401A }),
                                Color(sermon.videoGradientColors.getOrElse(2) { 0xFFD4A345 })
                            )
                        )
                    )
            ) {
                // Subtle dark wash for readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f))
                )

                // Center Circular White Play Button
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color.White.copy(alpha = 0.8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color(0xFF1E1B18),
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Bottom Left Series Chip
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 14.dp, bottom = 12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.65f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = sermon.series,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }

                // Bottom Right Duration Chip
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 14.dp, bottom = 12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.65f))
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = sermon.duration,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            // Card Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                Text(
                    text = sermon.title,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = LsgcfTextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sermon.speaker,
                        fontSize = 13.sp,
                        color = LsgcfTextMuted
                    )

                    Text(
                        text = sermon.date,
                        fontSize = 13.sp,
                        color = LsgcfTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun SermonPlayerDialog(
    sermon: SermonVideo,
    onDismiss: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0.28f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sermon_player_dialog")
            ) {
                // Top close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sermon.series,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfGold,
                        letterSpacing = 1.sp
                    )

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = LsgcfTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Simulated Video Screen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(sermon.videoGradientColors.getOrElse(0) { 0xFF2E1A10 }),
                                    Color(sermon.videoGradientColors.getOrElse(1) { 0xFF8A401A })
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color(0xFF1E1B18),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title & Preacher
                Text(
                    text = sermon.title,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = LsgcfTextPrimary
                )

                Text(
                    text = "${sermon.speaker} • ${sermon.scriptureRef}",
                    fontSize = 13.sp,
                    color = LsgcfTextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Scrubber Bar
                Slider(
                    value = progress,
                    onValueChange = { progress = it },
                    colors = SliderDefaults.colors(
                        thumbColor = LsgcfGold,
                        activeTrackColor = LsgcfGold,
                        inactiveTrackColor = LsgcfCardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("13:30", fontSize = 11.sp, color = LsgcfTextMuted)
                    Text(sermon.duration, fontSize = 11.sp, color = LsgcfTextMuted)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Summary Note
                Text(
                    text = sermon.description,
                    fontSize = 13.sp,
                    color = LsgcfTextPrimary,
                    lineHeight = 18.sp
                )
            }
        }
    )
}
