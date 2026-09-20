package com.example.data.model

data class DonationRecord(
    val id: String,
    val amount: Double,
    val fundCategory: String, // "General Tithes & Offering", "Building & Sanctuary Expansion", "World Missions", "Sunday School Supplies", "Benevolence & Food Pantry"
    val paymentMethod: String, // "Credit / Debit Card", "Bank Direct Transfer", "Google Pay"
    val frequency: String, // "One-Time Gift", "Weekly Recurring", "Monthly Recurring"
    val referenceCode: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Completed"
)
