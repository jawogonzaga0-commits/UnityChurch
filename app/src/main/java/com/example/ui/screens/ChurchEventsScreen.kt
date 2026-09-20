package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ChurchEvent
import com.example.data.model.VolunteerRole
import com.example.data.repository.ChurchRepository
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SacredCrimson
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@Composable
fun ChurchEventsScreen(
    churchRepository: ChurchRepository,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val churchProfile = churchRepository.churchProfile
    val events by churchRepository.getAllEvents().collectAsStateWithLifecycle(initialValue = emptyList())
    val volunteers by churchRepository.getAllVolunteerRoles().collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedSectionTab by remember { mutableIntStateOf(initialTab) } // 0 = Events & RSVP, 1 = Volunteer Signups, 2 = Calendar View, 3 = Church Profile
    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var selectedCalendarDay by remember { mutableIntStateOf(20) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("church_events_screen")
    ) {
        // Section Navigation Tabs
        TabRow(
            selectedTabIndex = selectedSectionTab,
            containerColor = DeepNavy,
            contentColor = Color.White
        ) {
            val tabs = listOf("Services & RSVP", "Volunteer Roles", "Calendar", "Parish Profile")
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedSectionTab == index,
                    onClick = { selectedSectionTab = index },
                    modifier = Modifier.testTag("events_tab_$index")
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selectedSectionTab == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedSectionTab == index) HolyGold else Color.White,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        when (selectedSectionTab) {
            0 -> {
                // Events & RSVP list
                Column(modifier = Modifier.fillMaxSize()) {
                    // Category filter chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val categories = listOf("All", "Worship Service", "Fellowship", "Community Outreach", "Prayer Vigil", "Youth")
                        categories.forEach { cat ->
                            FilterChip(
                                selected = selectedCategoryFilter == cat,
                                onClick = { selectedCategoryFilter = cat },
                                label = { Text(cat) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = HolyGold,
                                    selectedLabelColor = DeepNavy
                                )
                            )
                        }
                    }

                    val filteredEvents = if (selectedCategoryFilter == "All") {
                        events
                    } else {
                        events.filter { it.category == selectedCategoryFilter }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(filteredEvents) { event ->
                            EventCard(
                                event = event,
                                onRsvpChange = { newStatus ->
                                    coroutineScope.launch {
                                        churchRepository.setRsvpStatus(event.id, event.userRsvpStatus, newStatus)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            1 -> {
                // Volunteer Ministry Signups
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Ministry Volunteer Signups",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                            Text(
                                text = "Equipping every member to serve the body of Christ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Handshake,
                            contentDescription = "Volunteers",
                            tint = HolyGold,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(volunteers) { role ->
                            VolunteerCard(
                                role = role,
                                onToggleSignup = {
                                    coroutineScope.launch {
                                        churchRepository.toggleVolunteerRole(role.id, role.userSignedUp)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            2 -> {
                // Integrated Calendar View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "September 2026 Parish Calendar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "Tap a day to see congregational gatherings and scheduled activities",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Calendar Day Picker Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val days = listOf(
                            19 to "Sat", 20 to "Sun", 21 to "Mon", 22 to "Tue",
                            23 to "Wed", 24 to "Thu", 25 to "Fri", 26 to "Sat", 27 to "Sun"
                        )

                        days.forEach { (day, label) ->
                            val isSelected = day == selectedCalendarDay
                            val hasEvent = day == 20 || day == 23 || day == 25 || day == 26 || day == 27
                            Card(
                                modifier = Modifier
                                    .size(width = 54.dp, height = 72.dp)
                                    .clickable { selectedCalendarDay = day }
                                    .testTag("cal_day_$day"),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) DeepNavy else Color.White
                                ),
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) SoftGold else Color.Gray
                                    )
                                    Text(
                                        text = "$day",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else DeepNavy
                                    )
                                    if (hasEvent) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(if (isSelected) HolyGold else OliveSage)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Schedule for Day $selectedCalendarDay",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val calendarEvents = when (selectedCalendarDay) {
                            20 -> events.filter { it.category == "Worship Service" || it.category == "Youth" }
                            23 -> events.filter { it.category == "Fellowship" }
                            25 -> events.filter { it.category == "Prayer Vigil" }
                            26 -> events.filter { it.category == "Community Outreach" }
                            else -> events
                        }

                        if (calendarEvents.isEmpty()) {
                            item {
                                Text(
                                    text = "No public gatherings scheduled. Personal prayer room open 7am - 8pm.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        } else {
                            items(calendarEvents) { event ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = event.title,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = DeepNavy
                                            )
                                            Text(
                                                text = event.category,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = OliveSage,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "⏰ ${event.time} • 📍 ${event.location}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            3 -> {
                // Church Profile & Parish Info
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DeepNavy)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Church,
                                        contentDescription = "Church",
                                        tint = HolyGold,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = churchProfile.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Est. ${churchProfile.establishedYear} • ${churchProfile.denomination}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SoftGold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "\"${churchProfile.missionStatement}\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "Senior Pastors: ${churchProfile.seniorPastor}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = HolyGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Weekly Service Times",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                churchProfile.serviceTimes.forEach { service ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Schedule,
                                            contentDescription = "Time",
                                            tint = HolyGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = service,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Parish Contact & Location",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Icon(Icons.Default.LocationOn, contentDescription = "Address", tint = HolyGold, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(churchProfile.address, style = MaterialTheme.typography.bodyMedium)
                                }

                                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Icon(Icons.Default.Phone, contentDescription = "Phone", tint = HolyGold, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(churchProfile.phone, style = MaterialTheme.typography.bodyMedium)
                                }

                                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Icon(Icons.Default.Email, contentDescription = "Email", tint = HolyGold, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(churchProfile.email, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: ChurchEvent,
    onRsvpChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("event_card_${event.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📅 ${event.date} • ⏰ ${event.time}",
                        style = MaterialTheme.typography.labelSmall,
                        color = HolyGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DeepNavy.copy(alpha = 0.08f)
                ) {
                    Text(
                        text = "${event.rsvpCount} Attending",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📍 ${event.location}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // RSVP Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your RSVP:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepNavy
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val isAttending = event.userRsvpStatus == "Attending"
                    val isInterested = event.userRsvpStatus == "Interested"

                    Button(
                        onClick = { onRsvpChange(if (isAttending) "None" else "Attending") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isAttending) OliveSage else Color.LightGray.copy(alpha = 0.3f),
                            contentColor = if (isAttending) Color.White else DeepNavy
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("rsvp_attending_${event.id}")
                    ) {
                        if (isAttending) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text("Attending", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = { onRsvpChange(if (isInterested) "None" else "Interested") },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isInterested) HolyGold else Color.DarkGray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("rsvp_interested_${event.id}")
                    ) {
                        Text(if (isInterested) "★ Interested" else "Interested", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Composable
fun VolunteerCard(
    role: VolunteerRole,
    onToggleSignup: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("volunteer_card_${role.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = role.roleName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )
                Text(
                    text = "${role.filledSlots} / ${role.neededSlots} Filled",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (role.filledSlots >= role.neededSlots) OliveSage else SacredCrimson,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "For: ${role.eventTitle} • ${role.timeSlot}",
                style = MaterialTheme.typography.labelSmall,
                color = HolyGold,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = role.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            val progress = (role.filledSlots.toFloat() / role.neededSlots.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (progress >= 1f) OliveSage else HolyGold,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onToggleSignup,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (role.userSignedUp) OliveSage else DeepNavy
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("signup_volunteer_${role.id}")
                ) {
                    Text(
                        text = if (role.userSignedUp) "✓ Signed Up (Cancel)" else "Sign Up to Serve",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}
