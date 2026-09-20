package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserProfile
import com.example.data.repository.AnalyticsRepository
import com.example.data.repository.UserProfileRepository
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@Composable
fun NetworkAnalyticsScreen(
    userProfileRepository: UserProfileRepository,
    analyticsRepository: AnalyticsRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val profile by userProfileRepository.getUserProfile().collectAsStateWithLifecycle(initialValue = UserProfile())
    val summary by analyticsRepository.getMinistryEngagementSummary().collectAsStateWithLifecycle(initialValue = null)
    val attendanceTrends by analyticsRepository.getWeeklyAttendanceTrend().collectAsStateWithLifecycle(initialValue = emptyList())
    val ministryStats by analyticsRepository.getMinistryBreakdownStats().collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Member Connection & Networking, 1 = Real-Time Ministry Analytics

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("network_analytics_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DeepNavy,
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.testTag("tab_networking")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = if (selectedTab == 0) HolyGold else Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Member Network Card",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) HolyGold else Color.White
                    )
                }
            }
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.testTag("tab_analytics")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Insights, contentDescription = null, tint = if (selectedTab == 1) HolyGold else Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Ministry Analytics",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) HolyGold else Color.White
                    )
                }
            }
        }

        if (selectedTab == 0) {
            // Member Networking & Shareable Connection Card
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Digital Member Profile Pass
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("member_profile_card"),
                        colors = CardDefaults.cardColors(containerColor = DeepNavy),
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
                                            .clip(CircleShape)
                                            .background(HolyGold),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = profile.fullName.take(2).uppercase(),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepNavy
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = profile.fullName,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = profile.titleOrRole,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SoftGold
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Default.QrCode,
                                    contentDescription = "QR Code",
                                    tint = HolyGold,
                                    modifier = Modifier.size(44.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Home Church: ${profile.homeChurch}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                text = "Parish Member Since: ${profile.memberSince}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Spiritual Gifts:",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoftGold,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                profile.spiritualGifts.forEach { gift ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = HolyGold.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = gift,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SoftGold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "\"${profile.favoriteVerse}\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Share profile button
                            Button(
                                onClick = {
                                    val shareText = "Bible Connect Member Pass:\nName: ${profile.fullName}\nRole: ${profile.titleOrRole}\nChurch: ${profile.homeChurch}\nGifts: ${profile.spiritualGifts.joinToString(", ")}\nEmail: ${if (profile.shareContactEmail) profile.email else "(Private)"}\nPhone: ${if (profile.shareContactPhone) profile.phone else "(Private)"}"
                                    val intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, shareText)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(intent, "Share Member Contact Card"))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = HolyGold),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth().testTag("share_profile_btn")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, tint = DeepNavy, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Share Digital Connection Card", color = DeepNavy, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Privacy & Visibility Controls
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Congregation Visibility & Privacy",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Display Email in Ministry Directory", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    Text(profile.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                                Switch(
                                    checked = profile.shareContactEmail,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            userProfileRepository.updateProfile(profile.copy(shareContactEmail = it))
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Display Phone for Prayer Chain", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    Text(profile.phone, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                                Switch(
                                    checked = profile.shareContactPhone,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            userProfileRepository.updateProfile(profile.copy(shareContactPhone = it))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Real-Time Ministry Engagement Analytics
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Executive KPIs Overview
                item {
                    summary?.let { s ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = DeepNavy),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Congregation Engagement KPIs",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftGold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("${s.totalActiveMembers}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text("Active Parishioners", style = MaterialTheme.typography.labelSmall, color = SoftGold)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("${s.weeklyAverageAttendance}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = HolyGold)
                                        Text("Sunday Average", style = MaterialTheme.typography.labelSmall, color = SoftGold)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("${s.volunteerSignupsThisMonth}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text("Volunteers Served", style = MaterialTheme.typography.labelSmall, color = SoftGold)
                                    }
                                }
                            }
                        }
                    }
                }

                // Weekly Attendance Trend Bar Chart
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Weekly Attendance Trajectory",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = OliveSage, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+8.2% MoM", style = MaterialTheme.typography.labelSmall, color = OliveSage, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Custom Bar Chart representation
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                val maxVal = attendanceTrends.maxOfOrNull { it.count } ?: 400
                                attendanceTrends.forEach { pt ->
                                    val barHeightRatio = (pt.count.toFloat() / maxVal.toFloat()).coerceIn(0.2f, 1f)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Bottom,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "${pt.count}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (pt.isHighlight) HolyGold else DeepNavy,
                                            fontSize = 10.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(26.dp)
                                                .height((100 * barHeightRatio).dp)
                                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                                .background(if (pt.isHighlight) HolyGold else DeepNavy.copy(alpha = 0.8f))
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = pt.weekLabel,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.Gray,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Ministry Breakdown Stats
                item {
                    Text(
                        text = "Ministry Optimization & Volunteer Fill Rates",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                }

                items(ministryStats) { stat ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stat.ministryName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Text(
                                    text = "+${stat.growthPercentage}% YoY",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = OliveSage
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${stat.activeParticipants} Active Disciples • ${stat.targetGoal}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { stat.volunteerFillRate },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (stat.volunteerFillRate > 0.85f) OliveSage else HolyGold,
                                trackColor = Color.LightGray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}
