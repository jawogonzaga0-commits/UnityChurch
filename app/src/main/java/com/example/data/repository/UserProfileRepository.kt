package com.example.data.repository

import com.example.data.local.UserProfileDao
import com.example.data.local.UserProfileEntity
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepository(private val userProfileDao: UserProfileDao) {

    suspend fun seedInitialProfileIfEmpty() {
        val defaultProfile = UserProfileEntity(
            id = "usr_current_01",
            fullName = "Sarah Jenkins",
            titleOrRole = "Congregation Member & Study Leader",
            email = "sarah.jenkins@faithcommunity.org",
            phone = "+1 (555) 382-9104",
            homeChurch = "Grace & Truth Community Church",
            memberSince = "April 2021",
            spiritualGiftsSerialized = "Teaching,Hospitality,Prayer Intercession",
            favoriteVerse = "Philippians 4:13 — I can do all things through Christ who strengthens me.",
            bio = "Passionate about discipleship, women's Bible studies, and Sunday school ministry. Always happy to pray with fellow brothers and sisters.",
            shareContactPhone = true,
            shareContactEmail = true,
            isGroupLeader = true
        )
        userProfileDao.saveUserProfile(defaultProfile)
    }

    fun getUserProfile(): Flow<UserProfile> {
        return userProfileDao.getUserProfile("usr_current_01").map { entity ->
            if (entity != null) {
                UserProfile(
                    id = entity.id,
                    fullName = entity.fullName,
                    titleOrRole = entity.titleOrRole,
                    email = entity.email,
                    phone = entity.phone,
                    homeChurch = entity.homeChurch,
                    memberSince = entity.memberSince,
                    spiritualGifts = entity.spiritualGiftsSerialized.split(",").filter { it.isNotBlank() },
                    favoriteVerse = entity.favoriteVerse,
                    bio = entity.bio,
                    shareContactPhone = entity.shareContactPhone,
                    shareContactEmail = entity.shareContactEmail,
                    isGroupLeader = entity.isGroupLeader
                )
            } else {
                UserProfile()
            }
        }
    }

    suspend fun updateProfile(profile: UserProfile) {
        userProfileDao.saveUserProfile(
            UserProfileEntity(
                id = profile.id,
                fullName = profile.fullName,
                titleOrRole = profile.titleOrRole,
                email = profile.email,
                phone = profile.phone,
                homeChurch = profile.homeChurch,
                memberSince = profile.memberSince,
                spiritualGiftsSerialized = profile.spiritualGifts.joinToString(","),
                favoriteVerse = profile.favoriteVerse,
                bio = profile.bio,
                shareContactPhone = profile.shareContactPhone,
                shareContactEmail = profile.shareContactEmail,
                isGroupLeader = profile.isGroupLeader
            )
        )
    }
}
