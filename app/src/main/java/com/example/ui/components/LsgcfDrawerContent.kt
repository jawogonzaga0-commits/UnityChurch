package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfGoldLight
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

data class LsgcfDrawerItem(
    val id: String,
    val title: String,
    val subtitle: String? = null
)

@Composable
fun LsgcfDrawerContent(
    currentDestination: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        LsgcfDrawerItem("home", "Home"),
        LsgcfDrawerItem("bible", "Bible"),
        LsgcfDrawerItem("doctrine", "Doctrine", "Theology & core beliefs"),
        LsgcfDrawerItem("sunday_school", "Sunday School", "Weekly teaching materials"),
        LsgcfDrawerItem("announcements", "Announcements", "News & updates"),
        LsgcfDrawerItem("12_lessons", "12 Lessons", "Discipleship curriculum"),
        LsgcfDrawerItem("statement_of_faith", "Statement of Faith", "What we believe"),
        LsgcfDrawerItem("quizzes", "Quizzes", "Bible knowledge tests"),
        LsgcfDrawerItem("sermons", "Sermon Videos", "Sunday messages"),
        LsgcfDrawerItem("church_activity", "Church Activity", "This week")
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color.White)
            .statusBarsPadding()
            .padding(bottom = 24.dp)
            .testTag("lsgcf_drawer")
    ) {
        // Drawer Header with Cross & LSGCF
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Golden Cross
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Cross",
                tint = LsgcfGold,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "LSGCF",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = LsgcfTextPrimary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "La Salle Green Hills Community Fellowship",
                    fontSize = 12.sp,
                    color = LsgcfTextMuted,
                    lineHeight = 16.sp
                )
            }
        }

        HorizontalDivider(color = LsgcfCardBorder, thickness = 0.8.dp)

        // Menu Items List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            items.forEach { item ->
                val isSelected = currentDestination == item.id
                val itemBg = if (isSelected) LsgcfGoldLight.copy(alpha = 0.5f) else Color.Transparent

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(itemBg)
                        .clickable { onNavigate(item.id) }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left vertical gold indicator bar for active item
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(3.5.dp)
                                .height(38.dp)
                                .background(LsgcfGold)
                        )
                        Spacer(modifier = Modifier.width(20.5.dp))
                    } else {
                        Spacer(modifier = Modifier.width(24.dp))
                    }

                    Column {
                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) LsgcfGold else LsgcfTextPrimary
                        )

                        if (item.subtitle != null) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.subtitle,
                                fontSize = 12.sp,
                                color = LsgcfTextMuted
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = LsgcfCardBorder, thickness = 0.8.dp)

        // Bottom scripture quote from Philippians 1:21
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 16.dp, end = 24.dp)
        ) {
            Text(
                text = "\"For to me, to live is Christ.\"",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = LsgcfTextMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "PHIL 1:21",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LsgcfGold.copy(alpha = 0.85f),
                letterSpacing = 1.sp
            )
        }
    }
}
