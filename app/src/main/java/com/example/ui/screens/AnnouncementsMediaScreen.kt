package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.data.model.ChurchAnnouncement
import com.example.data.model.MediaRecap
import com.example.data.repository.ChurchRepository
import com.example.notification.NotificationHelper
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SacredCrimson
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@Composable
fun AnnouncementsMediaScreen(
    churchRepository: ChurchRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val announcements by churchRepository.getAllAnnouncements().collectAsStateWithLifecycle(initialValue = emptyList())
    val mediaRecaps by churchRepository.getAllMediaRecaps().collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Congregational Feed, 1 = Media & Event Recaps
    var showNewAnnouncementDialog by remember { mutableStateOf(false) }
    var showUploadMediaDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("announcements_media_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = DeepNavy,
                contentColor = Color.White
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.testTag("feed_tab")
                ) {
                    Text(
                        text = "Announcements Feed",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) HolyGold else Color.White,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.testTag("media_tab")
                ) {
                    Text(
                        text = "Pictures & Recaps",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) HolyGold else Color.White,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            if (selectedTab == 0) {
                // Announcements & Urgent Prayer Requests Feed
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.35f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = "Feed",
                                    tint = DeepNavy,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Real-Time Parish Bulletin",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy
                                    )
                                    Text(
                                        text = "Push notifications are broadcast immediately for urgent prayer needs and schedule modifications.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = DeepNavy.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }

                    items(announcements) { ann ->
                        AnnouncementCard(
                            announcement = ann,
                            onTriggerPush = {
                                NotificationHelper.showUrgentBroadcastNotification(
                                    context = context,
                                    title = ann.title,
                                    message = ann.content
                                )
                            }
                        )
                    }
                }
            } else {
                // Media Gallery & Event Recaps
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(mediaRecaps) { recap ->
                        MediaRecapCard(recap = recap)
                    }
                }
            }
        }

        // Floating action button depending on tab
        FloatingActionButton(
            onClick = {
                if (selectedTab == 0) {
                    showNewAnnouncementDialog = true
                } else {
                    showUploadMediaDialog = true
                }
            },
            containerColor = HolyGold,
            contentColor = DeepNavy,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("feed_fab")
        ) {
            Icon(
                imageVector = if (selectedTab == 0) Icons.Default.Campaign else Icons.Default.AddPhotoAlternate,
                contentDescription = if (selectedTab == 0) "Post Announcement" else "Upload Media"
            )
        }
    }

    // New Announcement / Prayer Request Dialog
    if (showNewAnnouncementDialog) {
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var isUrgent by remember { mutableStateOf(false) }
        var category by remember { mutableStateOf("Urgent Prayer") }

        AlertDialog(
            onDismissRequest = { showNewAnnouncementDialog = false },
            title = { Text("Broadcast Congregation Announcement", fontWeight = FontWeight.Bold, color = DeepNavy) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title / Headline") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Announcement Details or Prayer Need") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Urgent Push Notification Alert",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Switch(
                            checked = isUrgent,
                            onCheckedChange = { isUrgent = it }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            coroutineScope.launch {
                                churchRepository.addAnnouncement(
                                    title = title,
                                    content = content,
                                    author = "Sarah Jenkins",
                                    authorRole = "Ministry Leader",
                                    category = if (isUrgent) "Urgent Prayer" else "General Announcement",
                                    isUrgent = isUrgent
                                )
                                if (isUrgent) {
                                    NotificationHelper.showUrgentBroadcastNotification(
                                        context = context,
                                        title = title,
                                        message = content
                                    )
                                }
                            }
                            showNewAnnouncementDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("Broadcast Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewAnnouncementDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Upload Media Dialog
    if (showUploadMediaDialog) {
        var recapTitle by remember { mutableStateOf("") }
        var eventName by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var mediaUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1544427920-c49ccfb85579?w=800") }
        var mediaType by remember { mutableStateOf("Photo") }

        AlertDialog(
            onDismissRequest = { showUploadMediaDialog = false },
            title = { Text("Upload Event Recap & Media", fontWeight = FontWeight.Bold, color = DeepNavy) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = recapTitle,
                        onValueChange = { recapTitle = it },
                        label = { Text("Recap Title (e.g., Youth Camp 2026)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = eventName,
                        onValueChange = { eventName = it },
                        label = { Text("Associated Ministry Event") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Story & Testimonies") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    OutlinedTextField(
                        value = mediaUrl,
                        onValueChange = { mediaUrl = it },
                        label = { Text("Photo or Video Preview URL") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (recapTitle.isNotBlank()) {
                            coroutineScope.launch {
                                churchRepository.addMediaRecap(
                                    title = recapTitle,
                                    description = description,
                                    mediaType = mediaType,
                                    mediaUrl = mediaUrl,
                                    eventName = eventName,
                                    author = "Sarah Jenkins (Member)"
                                )
                            }
                            showUploadMediaDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("Upload & Share")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUploadMediaDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AnnouncementCard(
    announcement: ChurchAnnouncement,
    onTriggerPush: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("announcement_${announcement.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (announcement.isUrgent) SacredCrimson.copy(alpha = 0.08f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (announcement.isUrgent) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SacredCrimson
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.PriorityHigh, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "URGENT ALERT",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = DeepNavy.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = announcement.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = DeepNavy,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "By ${announcement.author} (${announcement.authorRole})",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = announcement.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (announcement.isUrgent) SacredCrimson else DeepNavy
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = announcement.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onTriggerPush,
                    modifier = Modifier.testTag("push_notification_btn_${announcement.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Push",
                        tint = if (announcement.isUrgent) SacredCrimson else HolyGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Simulate Device Push Alert",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (announcement.isUrgent) SacredCrimson else DeepNavy,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun MediaRecapCard(recap: MediaRecap) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("media_card_${recap.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) {
                AsyncImage(
                    model = recap.mediaUrl,
                    contentDescription = recap.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Category overlay pill
                Surface(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart),
                    shape = RoundedCornerShape(16.dp),
                    color = DeepNavy.copy(alpha = 0.75f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (recap.mediaType.contains("Video")) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = HolyGold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = recap.mediaType,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recap.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Event: ${recap.eventName} • ${recap.date}",
                    style = MaterialTheme.typography.labelSmall,
                    color = HolyGold,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = recap.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
