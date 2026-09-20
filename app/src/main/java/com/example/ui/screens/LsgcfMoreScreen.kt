package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfDivider
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfGoldLight
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

@Composable
fun LsgcfMoreScreen(
    onNavigateToGiving: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToAnnouncements: () -> Unit,
    onNavigateToVolunteers: () -> Unit,
    onNavigateToSundaySchool: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAboutChurch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_more_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
            Text(
                text = "LSGCF",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LsgcfTextCategory,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Fellowship & Ministries",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = LsgcfTextPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Ministries Card
            Text(
                text = "CONGREGATION & SERVICE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LsgcfTextCategory,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MoreNavRow(
                        icon = Icons.Default.Favorite,
                        title = "Tithes & Online Giving",
                        subtitle = "Support church ministries & missions",
                        onClick = onNavigateToGiving
                    )
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    MoreNavRow(
                        icon = Icons.Default.Groups,
                        title = "Small Groups & Fellowship",
                        subtitle = "Find a community discipleship group",
                        onClick = onNavigateToGroups
                    )
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    MoreNavRow(
                        icon = Icons.Default.Handshake,
                        title = "Volunteer Ministry Signups",
                        subtitle = "Ushering, worship, media & logistics",
                        onClick = onNavigateToVolunteers
                    )
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    MoreNavRow(
                        icon = Icons.Default.School,
                        title = "Sunday School Portal",
                        subtitle = "Curriculum & automated attendance logging",
                        onClick = onNavigateToSundaySchool
                    )
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    MoreNavRow(
                        icon = Icons.Default.Campaign,
                        title = "Announcements & Feed",
                        subtitle = "Real-time updates, urgent prayers & media recaps",
                        onClick = onNavigateToAnnouncements
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Account & About
            Text(
                text = "ABOUT & ACCOUNT",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LsgcfTextCategory,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MoreNavRow(
                        icon = Icons.Default.Person,
                        title = "Member Profile & Notes",
                        subtitle = "Pastoral contact & personal highlights",
                        onClick = onNavigateToProfile
                    )
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)

                    MoreNavRow(
                        icon = Icons.Default.Info,
                        title = "About LSGCF",
                        subtitle = "La Salle Green Hills Community Fellowship",
                        onClick = onNavigateToAboutChurch
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MoreNavRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(LsgcfGoldLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = LsgcfGold,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = LsgcfTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = LsgcfTextMuted
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = LsgcfTextCategory,
            modifier = Modifier.size(18.dp)
        )
    }
}
