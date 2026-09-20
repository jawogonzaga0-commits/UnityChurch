package com.example.data.repository

import com.example.data.local.DonationDao
import com.example.data.local.DonationEntity
import com.example.data.model.DonationRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DonationRepository(private val donationDao: DonationDao) {

    suspend fun seedInitialDataIfEmpty() {
        val initialDonations = listOf(
            DonationEntity(
                id = "don_01",
                amount = 150.00,
                fundCategory = "General Tithes & Offering",
                paymentMethod = "Credit Card (ending 4092)",
                frequency = "Monthly Recurring",
                referenceCode = "GIV-2026-99124",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7,
                status = "Completed"
            ),
            DonationEntity(
                id = "don_02",
                amount = 50.00,
                fundCategory = "Benevolence & Food Pantry",
                paymentMethod = "Google Pay",
                frequency = "One-Time Gift",
                referenceCode = "GIV-2026-88401",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 16,
                status = "Completed"
            ),
            DonationEntity(
                id = "don_03",
                amount = 35.00,
                fundCategory = "Sunday School Supplies",
                paymentMethod = "Bank Direct Transfer",
                frequency = "One-Time Gift",
                referenceCode = "GIV-2026-77319",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 28,
                status = "Completed"
            )
        )
        for (d in initialDonations) {
            donationDao.insertDonation(d)
        }
    }

    fun getAllDonations(): Flow<List<DonationRecord>> {
        return donationDao.getAllDonations().map { list ->
            list.map { entity ->
                DonationRecord(
                    id = entity.id,
                    amount = entity.amount,
                    fundCategory = entity.fundCategory,
                    paymentMethod = entity.paymentMethod,
                    frequency = entity.frequency,
                    referenceCode = entity.referenceCode,
                    timestamp = entity.timestamp,
                    status = entity.status
                )
            }
        }
    }

    suspend fun processDonation(
        amount: Double,
        fundCategory: String,
        paymentMethod: String,
        frequency: String
    ): DonationRecord {
        val record = DonationRecord(
            id = "don_" + UUID.randomUUID().toString().take(8),
            amount = amount,
            fundCategory = fundCategory,
            paymentMethod = paymentMethod,
            frequency = frequency,
            referenceCode = "GIV-2026-" + (10000..99999).random(),
            timestamp = System.currentTimeMillis(),
            status = "Completed"
        )
        donationDao.insertDonation(
            DonationEntity(
                id = record.id,
                amount = record.amount,
                fundCategory = record.fundCategory,
                paymentMethod = record.paymentMethod,
                frequency = record.frequency,
                referenceCode = record.referenceCode,
                timestamp = record.timestamp,
                status = record.status
            )
        )
        return record
    }
}
