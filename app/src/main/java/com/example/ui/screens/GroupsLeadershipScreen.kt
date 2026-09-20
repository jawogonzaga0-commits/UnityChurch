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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.StudyGroup
import com.example.data.repository.CommunityRepository
import com.example.notification.NotificationHelper
import com.example.ui.components.EncryptionBadge
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SacredCrimson
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@Composable
fun GroupsLeadershipScreen(
    communityRepository: CommunityRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val groups by communityRepository.getAllGroups().collectAsStateWithLifecycle(initialValue = emptyList())

    // Active group for admin dashboard / chat
    val myLeadGroup = groups.find { it.isUserLeader } ?: groups.firstOrNull()
    val activeGroupId = myLeadGroup?.id ?: "grp_romans"

    val groupMembers by communityRepository.getMembersForGroup(activeGroupId).collectAsStateWithLifecycle(initialValue = emptyList())
    val meetingLogs by communityRepository.getMeetingLogs(activeGroupId).collectAsStateWithLifecycle(initialValue = emptyList())
    val messages by communityRepository.getGroupMessages(activeGroupId).collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Explore Groups, 1 = Leader Admin Dashboard, 2 = Encrypted Group Chat
    var showBroadcastDialog by remember { mutableStateOf(false) }
    var showLogMeetingDialog by remember { mutableStateOf(false) }
    var chatInputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("groups_leadership_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DeepNavy,
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.testTag("tab_explore_groups")
            ) {
                Text(
                    text = "Study Groups",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTab == 0) HolyGold else Color.White,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.testTag("tab_leader_dashboard")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = if (selectedTab == 1) HolyGold else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Leader Admin",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) HolyGold else Color.White
                    )
                }
            }
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                modifier = Modifier.testTag("tab_encrypted_chat")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (selectedTab == 2) HolyGold else Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Encrypted Chat",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 2) HolyGold else Color.White
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> {
                // Study & Prayer Groups List with Join/Leader applications
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SoftGold.copy(alpha = 0.25f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Prayer & Bible Study Circles",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Text(
                                    text = "You can apply to join any group as a member, or apply as a Group Leader to facilitate discussions and disciple believers.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DeepNavy.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    items(groups) { group ->
                        StudyGroupCard(
                            group = group,
                            onJoinAsMember = {
                                coroutineScope.launch {
                                    communityRepository.applyAsMember(group.id)
                                }
                            },
                            onApplyAsLeader = {
                                coroutineScope.launch {
                                    communityRepository.applyAsLeader(group.id)
                                }
                            }
                        )
                    }
                }
            }
            1 -> {
                // Simplified Leader Administrative Dashboard
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Dashboard Title Banner
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = DeepNavy),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Leader Administrative Console",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftGold
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = HolyGold
                                    ) {
                                        Text(
                                            text = "LEADER ACTIVE",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepNavy,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = myLeadGroup?.name ?: "Tuesday Romans & Reformed Theology Study",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = { showBroadcastDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = HolyGold),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).testTag("leader_broadcast_btn")
                                    ) {
                                        Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = DeepNavy, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Push Alert", color = DeepNavy, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = { showLogMeetingDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.18f)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).testTag("leader_log_attendance_btn")
                                    ) {
                                        Text("Log Meeting", color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Section: Manage Members & Approvals
                    item {
                        Text(
                            text = "Group Roster & Join Approvals (${groupMembers.size})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                    }

                    items(groupMembers) { member ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
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
                                        text = member.memberName,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy
                                    )
                                    Text(
                                        text = "Role: ${member.role} • Joined: ${member.joinDate}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    if (member.role.contains("Pending") || member.role.contains("Visitor")) {
                                        Button(
                                            onClick = {
                                                coroutineScope.launch {
                                                    communityRepository.approveMember(member.id)
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = OliveSage),
                                            shape = RoundedCornerShape(6.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                            modifier = Modifier.testTag("approve_member_${member.id}")
                                        ) {
                                            Text("Approve", style = MaterialTheme.typography.labelSmall)
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                communityRepository.removeMember(member.id)
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove", tint = SacredCrimson, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Section: Past Meeting Attendance Logs
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Meeting Attendance Logs",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                    }

                    items(meetingLogs) { log ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = log.date,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy
                                    )
                                    Text(
                                        text = "${log.attendeesCount}/${log.totalCount} Attended",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = OliveSage
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Scripture: ${log.scriptureCovered}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = HolyGold
                                )
                                Text(
                                    text = log.prayerRequestsSummary,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            2 -> {
                // Encrypted Group Chat
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Security / Encryption Header
                    EncryptionBadge(
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(messages) { msg ->
                            val isMe = msg.isFromMe
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
                            ) {
                                Text(
                                    text = "${msg.senderName} (${msg.senderRole})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = DeepNavy,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )

                                Card(
                                    shape = RoundedCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 14.dp,
                                        bottomStart = if (isMe) 14.dp else 2.dp,
                                        bottomEnd = if (isMe) 2.dp else 14.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isMe) DeepNavy else Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = msg.messageText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isMe) Color.White else DeepNavy
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Lock,
                                                contentDescription = "Encrypted",
                                                tint = if (isMe) SoftGold else OliveSage,
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "End-to-End Encrypted",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (isMe) SoftGold.copy(alpha = 0.8f) else Color.Gray,
                                                fontSize = 9.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Encrypted message input row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = chatInputText,
                            onValueChange = { chatInputText = it },
                            placeholder = { Text("Write encrypted message...") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("group_chat_input"),
                            shape = RoundedCornerShape(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (chatInputText.isNotBlank()) {
                                    coroutineScope.launch {
                                        communityRepository.sendEncryptedMessage(
                                            groupId = activeGroupId,
                                            senderName = "Sarah Jenkins",
                                            role = "Leader",
                                            text = chatInputText
                                        )
                                        chatInputText = ""
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(HolyGold)
                                .testTag("send_encrypted_msg_btn")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = DeepNavy)
                        }
                    }
                }
            }
        }
    }

    // Leader Broadcast Alert Dialog
    if (showBroadcastDialog) {
        var alertMessage by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showBroadcastDialog = false },
            title = { Text("Push Alert to Group Members", fontWeight = FontWeight.Bold, color = DeepNavy) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Broadcast an urgent prayer request or meeting schedule update immediately to all members' notification trays.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = alertMessage,
                        onValueChange = { alertMessage = it },
                        label = { Text("Alert Message") },
                        placeholder = { Text("e.g. Please pray for Marcus; Meeting location moved to Fellowship Hall") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (alertMessage.isNotBlank()) {
                            NotificationHelper.showGroupLeaderNotification(
                                context = context,
                                groupTitle = myLeadGroup?.name ?: "Romans Study",
                                updateText = alertMessage
                            )
                            showBroadcastDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("Push Notification Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBroadcastDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Leader Log Meeting Dialog
    if (showLogMeetingDialog) {
        var scripture by remember { mutableStateOf("Romans 9:1-15") }
        var attendeesStr by remember { mutableStateOf("13") }
        var notes by remember { mutableStateOf("Great discussion on divine sovereignty and evangelism. Prayed for upcoming baptisms.") }

        AlertDialog(
            onDismissRequest = { showLogMeetingDialog = false },
            title = { Text("Log Group Meeting Attendance", fontWeight = FontWeight.Bold, color = DeepNavy) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = scripture,
                        onValueChange = { scripture = it },
                        label = { Text("Scripture Passage Studied") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = attendeesStr,
                        onValueChange = { attendeesStr = it },
                        label = { Text("Number of Attendees Present") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Prayer Requests & Meeting Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val count = attendeesStr.toIntOrNull() ?: 12
                        coroutineScope.launch {
                            communityRepository.logMeetingAttendance(
                                groupId = activeGroupId,
                                date = "Today",
                                scripture = scripture,
                                attendees = count,
                                total = groupMembers.size,
                                notes = notes
                            )
                        }
                        showLogMeetingDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("Save Log")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogMeetingDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StudyGroupCard(
    group: StudyGroup,
    onJoinAsMember: () -> Unit,
    onApplyAsLeader: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("group_card_${group.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "Focus: ${group.focusBookOrTheme}",
                        style = MaterialTheme.typography.labelSmall,
                        color = HolyGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = DeepNavy.copy(alpha = 0.08f)
                ) {
                    Text(
                        text = "${group.memberCount} Members",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = group.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Leader: ${group.leaderName} • ⏰ ${group.meetingDayTime}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "📍 ${group.meetingLocation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (group.isUserMember) {
                    Button(
                        onClick = {},
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = OliveSage.copy(alpha = 0.2f), disabledContentColor = OliveSage),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("✓ You Are a Member", style = MaterialTheme.typography.labelSmall)
                    }
                } else {
                    Button(
                        onClick = onJoinAsMember,
                        colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).testTag("join_group_${group.id}")
                    ) {
                        Text("Join as Member", style = MaterialTheme.typography.labelSmall)
                    }
                }

                if (!group.isUserLeader) {
                    OutlinedButton(
                        onClick = onApplyAsLeader,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = HolyGold),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).testTag("apply_leader_${group.id}")
                    ) {
                        Text("Apply as Leader", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
