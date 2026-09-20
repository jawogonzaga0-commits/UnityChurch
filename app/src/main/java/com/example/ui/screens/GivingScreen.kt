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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.DonationRecord
import com.example.data.repository.DonationRepository
import com.example.ui.components.EncryptionBadge
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.OliveSage
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GivingScreen(
    donationRepository: DonationRepository,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val pastDonations by donationRepository.getAllDonations().collectAsStateWithLifecycle(initialValue = emptyList())

    val presetAmounts = listOf(25.0, 50.0, 100.0, 250.0, 500.0)
    var selectedAmount by remember { mutableStateOf(100.0) }
    var customAmountText by remember { mutableStateOf("") }
    var selectedFund by remember { mutableStateOf("General Tithes & Offering") }
    var selectedMethod by remember { mutableStateOf("Credit Card (Visa •••• 4092)") }
    var selectedFrequency by remember { mutableStateOf("One-Time Gift") }
    var recentReceipt by remember { mutableStateOf<DonationRecord?>(null) }
    var showReceiptDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("giving_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Giving Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DeepNavy),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Tithes & Offerings",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "2 Corinthians 9:7 — God loveth a cheerful giver.",
                                style = MaterialTheme.typography.bodySmall,
                                color = SoftGold,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.VolunteerActivism,
                            contentDescription = "Giving",
                            tint = HolyGold,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    EncryptionBadge(
                        algorithm = "PCI-DSS Level 1 & 256-Bit SSL Encrypted"
                    )
                }
            }
        }

        // Fund Category Selection
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. Select Ministry Fund or Event Fee",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val funds = listOf(
                        "General Tithes & Offering",
                        "Sanctuary & Building Expansion",
                        "World Missions & Church Planting",
                        "Benevolence & Food Pantry",
                        "Sunday School & Youth Camp Fee"
                    )

                    funds.forEach { fund ->
                        val isSelected = selectedFund == fund
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            color = if (isSelected) HolyGold.copy(alpha = 0.15f) else Color.Transparent,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) HolyGold else Color.LightGray.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = fund,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) DeepNavy else Color.DarkGray
                                )
                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = HolyGold, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Amount Selection
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. Select Gift Amount",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presetAmounts.forEach { amt ->
                            val isSelected = selectedAmount == amt && customAmountText.isBlank()
                            Button(
                                onClick = {
                                    selectedAmount = amt
                                    customAmountText = ""
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) DeepNavy else Color.LightGray.copy(alpha = 0.2f),
                                    contentColor = if (isSelected) Color.White else DeepNavy
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f).testTag("amt_${amt.toInt()}")
                            ) {
                                Text("$${amt.toInt()}", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = customAmountText,
                        onValueChange = {
                            customAmountText = it
                            val parsed = it.toDoubleOrNull()
                            if (parsed != null && parsed > 0) {
                                selectedAmount = parsed
                            }
                        },
                        label = { Text("Or Enter Custom Amount ($)") },
                        modifier = Modifier.fillMaxWidth().testTag("custom_amount_input")
                    )
                }
            }
        }

        // Payment Method & Frequency
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. Payment Method & Schedule",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val methods = listOf(
                        "Credit Card (Visa •••• 4092)",
                        "Google Pay (Direct)",
                        "Bank Account ACH (•••• 9144)"
                    )

                    methods.forEach { method ->
                        FilterChip(
                            selected = selectedMethod == method,
                            onClick = { selectedMethod = method },
                            label = { Text(method) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DeepNavy,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Frequency:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("One-Time Gift", "Weekly Recurring", "Monthly Recurring").forEach { freq ->
                            FilterChip(
                                selected = selectedFrequency == freq,
                                onClick = { selectedFrequency = freq },
                                label = { Text(freq) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = HolyGold,
                                    selectedLabelColor = DeepNavy
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val receipt = donationRepository.processDonation(
                                    amount = selectedAmount,
                                    fundCategory = selectedFund,
                                    paymentMethod = selectedMethod,
                                    frequency = selectedFrequency
                                )
                                recentReceipt = receipt
                                showReceiptDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_donation_btn")
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = SoftGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Authorize Giving of $${"%.2f".format(selectedAmount)}",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Giving History
        item {
            Text(
                text = "Recent Giving & Fee Receipts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DeepNavy
            )
        }

        items(pastDonations) { donation ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = donation.fundCategory,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = "${donation.paymentMethod} • Ref: ${donation.referenceCode}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = donation.frequency,
                            style = MaterialTheme.typography.labelSmall,
                            color = OliveSage,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$${"%.2f".format(donation.amount)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = OliveSage.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "VERIFIED",
                                style = MaterialTheme.typography.labelSmall,
                                color = OliveSage,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Receipt Confirmation Dialog
    if (showReceiptDialog && recentReceipt != null) {
        val r = recentReceipt!!
        AlertDialog(
            onDismissRequest = { showReceiptDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = OliveSage, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Donation Authorized", fontWeight = FontWeight.Bold, color = DeepNavy)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Thank you for your cheerful support of God's work!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("• Transaction Reference: ${r.referenceCode}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("• Amount: $${"%.2f".format(r.amount)}", style = MaterialTheme.typography.bodySmall)
                    Text("• Fund: ${r.fundCategory}", style = MaterialTheme.typography.bodySmall)
                    Text("• Frequency: ${r.frequency}", style = MaterialTheme.typography.bodySmall)
                    Text("• Method: ${r.paymentMethod}", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "A 501(c)(3) tax receipt statement has been logged to your parish profile.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showReceiptDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("View My History")
                }
            }
        )
    }
}
