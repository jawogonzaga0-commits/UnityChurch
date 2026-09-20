package com.example.data.model

data class UserProfile(
    val id: String = "usr_current_01",
    val fullName: String = "Sarah Jenkins",
    val titleOrRole: String = "Congregation Member & Study Leader",
    val email: String = "sarah.jenkins@faithcommunity.org",
    val phone: String = "+1 (555) 382-9104",
    val homeChurch: String = "Grace & Truth Community Church",
    val memberSince: String = "April 2021",
    val spiritualGifts: List<String> = listOf("Teaching", "Hospitality", "Prayer Intercession"),
    val favoriteVerse: String = "Philippians 4:13 — I can do all things through Christ who strengthens me.",
    val bio: String = "Passionate about discipleship, women's Bible studies, and Sunday school ministry. Always happy to pray with fellow brothers and sisters.",
    val shareContactPhone: Boolean = true,
    val shareContactEmail: Boolean = true,
    val isGroupLeader: Boolean = true
)
