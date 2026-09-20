package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DoctrinePillar
import com.example.data.repository.LsgcfRepository
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfDivider
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfGoldLight
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

@Composable
fun LsgcfDoctrineScreen(
    lsgcfRepository: LsgcfRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val doctrines = remember { lsgcfRepository.doctrines }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_doctrine_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LsgcfTextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "LSGCF",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Doctrine & Theology",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = LsgcfTextPrimary
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Biblical foundations and theological pillars confessed by La Salle Green Hills Community Fellowship.",
                        fontSize = 13.sp,
                        color = LsgcfTextMuted,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                items(doctrines) { doctrine ->
                    DoctrineCard(doctrine = doctrine)
                }

                item {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}

@Composable
private fun DoctrineCard(doctrine: DoctrinePillar) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, LsgcfCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doctrine.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = LsgcfTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = doctrine.subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = LsgcfGold
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = LsgcfTextCategory,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = doctrine.summary,
                fontSize = 13.sp,
                color = LsgcfTextPrimary,
                lineHeight = 19.sp
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = LsgcfDivider, thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "THEOLOGICAL EXPOSITION",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = doctrine.explanation,
                        fontSize = 13.sp,
                        color = LsgcfTextMuted,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "KEY SCRIPTURE PROOFS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        doctrine.keyVerses.forEach { ref ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(LsgcfGoldLight)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = ref,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LsgcfGold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
