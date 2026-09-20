package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ui.components.LsgcfBottomNav
import com.example.ui.components.LsgcfDrawerContent
import com.example.ui.components.LsgcfTab
import com.example.ui.screens.AnnouncementsMediaScreen
import com.example.ui.screens.ChurchEventsScreen
import com.example.ui.screens.GivingScreen
import com.example.ui.screens.GroupsLeadershipScreen
import com.example.ui.screens.LsgcfBibleReaderScreen
import com.example.ui.screens.LsgcfDiscipleshipScreen
import com.example.ui.screens.LsgcfDoctrineScreen
import com.example.ui.screens.LsgcfHomeScreen
import com.example.ui.screens.LsgcfMoreScreen
import com.example.ui.screens.LsgcfQuizzesScreen
import com.example.ui.screens.LsgcfSermonVideosScreen
import com.example.ui.screens.LsgcfStatementOfFaithScreen
import com.example.ui.screens.NetworkAnalyticsScreen
import com.example.ui.screens.SundaySchoolScreen
import com.example.ui.theme.BibleChurchTheme
import com.example.ui.theme.LsgcfBg
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as BibleChurchApplication

        setContent {
            BibleChurchTheme {
                MainAppScreen(app = app)
            }
        }
    }
}

@Composable
fun MainAppScreen(app: BibleChurchApplication) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var selectedTab by remember { mutableStateOf(LsgcfTab.HOME) }
    var activeDetailRoute by remember { mutableStateOf<String?>(null) }

    // Request notification permission on Android 13+
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* granted or denied */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Back handling
    BackHandler(enabled = drawerState.isOpen || activeDetailRoute != null || selectedTab != LsgcfTab.HOME) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else if (activeDetailRoute != null) {
            activeDetailRoute = null
        } else if (selectedTab != LsgcfTab.HOME) {
            selectedTab = LsgcfTab.HOME
        }
    }

    val currentDestinationId = activeDetailRoute ?: when (selectedTab) {
        LsgcfTab.HOME -> "home"
        LsgcfTab.BIBLE -> "bible"
        LsgcfTab.SERMONS -> "sermons"
        LsgcfTab.MORE -> "more"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerTonalElevation = 0.dp
            ) {
                LsgcfDrawerContent(
                    currentDestination = currentDestinationId,
                    onNavigate = { routeId ->
                        coroutineScope.launch { drawerState.close() }
                        when (routeId) {
                            "home" -> {
                                selectedTab = LsgcfTab.HOME
                                activeDetailRoute = null
                            }
                            "bible" -> {
                                selectedTab = LsgcfTab.BIBLE
                                activeDetailRoute = null
                            }
                            "sermons" -> {
                                selectedTab = LsgcfTab.SERMONS
                                activeDetailRoute = null
                            }
                            "more" -> {
                                selectedTab = LsgcfTab.MORE
                                activeDetailRoute = null
                            }
                            else -> {
                                activeDetailRoute = routeId
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                LsgcfBottomNav(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        activeDetailRoute = null
                        selectedTab = tab
                    }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = LsgcfBg
            ) {
                if (activeDetailRoute != null) {
                    when (activeDetailRoute) {
                        "doctrine" -> LsgcfDoctrineScreen(
                            lsgcfRepository = app.lsgcfRepository,
                            onBack = { activeDetailRoute = null }
                        )
                        "12_lessons" -> LsgcfDiscipleshipScreen(
                            lsgcfRepository = app.lsgcfRepository,
                            onBack = { activeDetailRoute = null }
                        )
                        "statement_of_faith" -> LsgcfStatementOfFaithScreen(
                            lsgcfRepository = app.lsgcfRepository,
                            onBack = { activeDetailRoute = null }
                        )
                        "quizzes" -> LsgcfQuizzesScreen(
                            lsgcfRepository = app.lsgcfRepository,
                            onBack = { activeDetailRoute = null }
                        )
                        "church_activity" -> ChurchEventsScreen(
                            churchRepository = app.churchRepository
                        )
                        "sunday_school" -> SundaySchoolScreen(
                            sundaySchoolRepository = app.sundaySchoolRepository
                        )
                        "announcements" -> AnnouncementsMediaScreen(
                            churchRepository = app.churchRepository
                        )
                        "giving" -> GivingScreen(
                            donationRepository = app.donationRepository
                        )
                        "groups" -> GroupsLeadershipScreen(
                            communityRepository = app.communityRepository
                        )
                        "volunteers" -> ChurchEventsScreen(
                            churchRepository = app.churchRepository,
                            initialTab = 1
                        )
                        "profile" -> NetworkAnalyticsScreen(
                            userProfileRepository = app.userProfileRepository,
                            analyticsRepository = app.analyticsRepository
                        )
                        "about_church" -> ChurchEventsScreen(
                            churchRepository = app.churchRepository,
                            initialTab = 3
                        )
                        else -> {
                            LsgcfHomeScreen(
                                onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                                onNavigateToSermons = {
                                    activeDetailRoute = null
                                    selectedTab = LsgcfTab.SERMONS
                                },
                                onNavigateToEvents = { activeDetailRoute = "church_activity" },
                                onNavigateToDoctrine = { activeDetailRoute = "doctrine" },
                                onNavigateToSundaySchool = { activeDetailRoute = "sunday_school" },
                                onNavigateTo12Lessons = { activeDetailRoute = "12_lessons" },
                                onNavigateToStatementOfFaith = { activeDetailRoute = "statement_of_faith" },
                                onNavigateToQuizzes = { activeDetailRoute = "quizzes" },
                                onOpenNotifications = { activeDetailRoute = "announcements" }
                            )
                        }
                    }
                } else {
                    when (selectedTab) {
                        LsgcfTab.HOME -> {
                            LsgcfHomeScreen(
                                onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                                onNavigateToSermons = { selectedTab = LsgcfTab.SERMONS },
                                onNavigateToEvents = { activeDetailRoute = "church_activity" },
                                onNavigateToDoctrine = { activeDetailRoute = "doctrine" },
                                onNavigateToSundaySchool = { activeDetailRoute = "sunday_school" },
                                onNavigateTo12Lessons = { activeDetailRoute = "12_lessons" },
                                onNavigateToStatementOfFaith = { activeDetailRoute = "statement_of_faith" },
                                onNavigateToQuizzes = { activeDetailRoute = "quizzes" },
                                onOpenNotifications = { activeDetailRoute = "announcements" }
                            )
                        }
                        LsgcfTab.BIBLE -> {
                            LsgcfBibleReaderScreen(
                                bibleRepository = app.bibleRepository,
                                onBack = { selectedTab = LsgcfTab.HOME }
                            )
                        }
                        LsgcfTab.SERMONS -> {
                            LsgcfSermonVideosScreen(
                                lsgcfRepository = app.lsgcfRepository,
                                onBack = { selectedTab = LsgcfTab.HOME }
                            )
                        }
                        LsgcfTab.MORE -> {
                            LsgcfMoreScreen(
                                onNavigateToGiving = { activeDetailRoute = "giving" },
                                onNavigateToGroups = { activeDetailRoute = "groups" },
                                onNavigateToAnnouncements = { activeDetailRoute = "announcements" },
                                onNavigateToVolunteers = { activeDetailRoute = "volunteers" },
                                onNavigateToSundaySchool = { activeDetailRoute = "sunday_school" },
                                onNavigateToProfile = { activeDetailRoute = "profile" },
                                onNavigateToAboutChurch = { activeDetailRoute = "about_church" }
                            )
                        }
                    }
                }
            }
        }
    }
}
