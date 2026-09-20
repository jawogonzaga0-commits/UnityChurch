package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary

enum class LsgcfTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Outlined.Home),
    BIBLE("Bible", Icons.Outlined.MenuBook),
    SERMONS("Sermons", Icons.Outlined.PlayCircle),
    MORE("More", Icons.Outlined.GridView)
}

@Composable
fun LsgcfBottomNav(
    selectedTab: LsgcfTab,
    onTabSelected: (LsgcfTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(LsgcfBg)
            .navigationBarsPadding()
            .testTag("lsgcf_bottom_nav")
    ) {
        HorizontalDivider(color = LsgcfCardBorder, thickness = 0.8.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LsgcfTab.values().forEach { tab ->
                val isSelected = selectedTab == tab
                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onTabSelected(tab) }
                        )
                        .padding(vertical = 4.dp)
                        .testTag("tab_${tab.name.lowercase()}")
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (isSelected) LsgcfTextPrimary else LsgcfTextMuted,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = tab.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) LsgcfTextPrimary else LsgcfTextMuted
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    // Small golden dot indicator underneath active tab
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(LsgcfGold)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
