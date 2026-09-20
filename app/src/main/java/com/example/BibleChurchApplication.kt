package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.repository.AnalyticsRepository
import com.example.data.repository.BibleRepository
import com.example.data.repository.ChurchRepository
import com.example.data.repository.CommunityRepository
import com.example.data.repository.DonationRepository
import com.example.data.repository.LsgcfRepository
import com.example.data.repository.SundaySchoolRepository
import com.example.data.repository.UserProfileRepository
import com.example.notification.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BibleChurchApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var bibleRepository: BibleRepository
        private set

    lateinit var churchRepository: ChurchRepository
        private set

    lateinit var sundaySchoolRepository: SundaySchoolRepository
        private set

    lateinit var communityRepository: CommunityRepository
        private set

    lateinit var donationRepository: DonationRepository
        private set

    lateinit var userProfileRepository: UserProfileRepository
        private set

    val lsgcfRepository: LsgcfRepository by lazy { LsgcfRepository() }

    val analyticsRepository: AnalyticsRepository by lazy { AnalyticsRepository() }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannels(this)

        database = AppDatabase.getDatabase(this)
        bibleRepository = BibleRepository(database.bibleDao())
        churchRepository = ChurchRepository(database.churchDao())
        sundaySchoolRepository = SundaySchoolRepository(database.sundaySchoolDao())
        communityRepository = CommunityRepository(database.groupDao())
        donationRepository = DonationRepository(database.donationDao())
        userProfileRepository = UserProfileRepository(database.userProfileDao())

        val applicationScope = CoroutineScope(Dispatchers.IO)
        applicationScope.launch {
            churchRepository.seedInitialDataIfEmpty()
            communityRepository.seedInitialDataIfEmpty()
            donationRepository.seedInitialDataIfEmpty()
            userProfileRepository.seedInitialProfileIfEmpty()
            sundaySchoolRepository.seedAttendanceIfEmpty("cls_primary")
            sundaySchoolRepository.seedAttendanceIfEmpty("cls_toddlers")
            sundaySchoolRepository.seedAttendanceIfEmpty("cls_juniors")
            sundaySchoolRepository.seedAttendanceIfEmpty("cls_youth")
            sundaySchoolRepository.seedAttendanceIfEmpty("cls_adults")
        }
    }
}
