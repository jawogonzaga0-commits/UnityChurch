package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyVerse
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HighlightEmerald
import com.example.ui.theme.HighlightGold
import com.example.ui.theme.HighlightPurple
import com.example.ui.theme.HighlightRose
import com.example.ui.theme.HighlightSky
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SoftGold

@Composable
fun EncryptionBadge(
    modifier: Modifier = Modifier,
    algorithm: String = "AES-256-GCM End-to-End Encrypted"
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .testTag("encryption_badge"),
        color = DeepNavy.copy(alpha = 0.08f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Encrypted",
                tint = OliveSage,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = algorithm,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = DeepNavy
            )
        }
    }
}

@Composable
fun HighlightColorPicker(
    selectedColorHex: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        "#FFF176" to HighlightGold,
        "#A5D6A7" to HighlightEmerald,
        "#90CAF9" to HighlightSky,
        "#F48FB1" to HighlightRose,
        "#CE93D8" to HighlightPurple
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("highlight_color_picker"),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { (hex, composeColor) ->
            val isSelected = selectedColorHex.equals(hex, ignoreCase = true)
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(composeColor)
                    .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) DeepNavy else Color.Black.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(hex) },
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = DeepNavy,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DailyVerseCard(
    dailyVerse: DailyVerse,
    onShare: () -> Unit,
    onTriggerNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("daily_verse_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepNavy
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(HolyGold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DAILY VERSE & DEVOTION",
                        style = MaterialTheme.typography.labelSmall,
                        color = SoftGold,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }
                IconButton(
                    onClick = onShare,
                    modifier = Modifier.size(32.dp).testTag("share_daily_verse")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Verse",
                        tint = SoftGold,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\"${dailyVerse.text}\"",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontStyle = FontStyle.Italic,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "— ${dailyVerse.reference}",
                style = MaterialTheme.typography.labelMedium,
                color = HolyGold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = dailyVerse.devotion,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onTriggerNotification() }
                        .testTag("test_notification_btn"),
                    color = HolyGold.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "🔔 Push To Notification Tray",
                        style = MaterialTheme.typography.labelSmall,
                        color = SoftGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
