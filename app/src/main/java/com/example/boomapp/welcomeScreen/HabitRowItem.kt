package com.example.boomapp.welcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data Model
data class HabitItemData(
    val id: Int,
    val iconRes: Int,
    val title: String,
    val subtitle: String,
    val isEnabled: Boolean = true
)
// 2. Individual Item Row
@Composable
fun HabitRowItem(
    item: HabitItemData,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val primaryBrown = Color(0xFFA65851)
    val lightIconBg = Color(0xFFFBF1EE)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading Icon with soft circular/squircle background
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(color = lightIconBg, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = null,
                tint = primaryBrown,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Title and Subtitle
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C1810)
            )
            if (item.subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    color = Color(0xFF8A7E78)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Custom Colored Switch
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = primaryBrown,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE2D7D3),
                uncheckedBorderColor = Color.Transparent,
                checkedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun HabitTrackerList(
    items: List<HabitItemData>,
    modifier: Modifier = Modifier
) {
    var stateList by remember { mutableStateOf(items) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Use Column here so the outer LazyColumn can measure and scroll it properly
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            stateList.forEachIndexed { index, item ->
                HabitRowItem(
                    item = item,
                    isChecked = item.isEnabled,
                    onCheckedChange = { isChecked ->
                        stateList = stateList.toMutableList().also {
                            it[index] = item.copy(isEnabled = isChecked)
                        }
                    }
                )

                // Divider line between items
                if (index < stateList.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 74.dp, end = 16.dp),
                        thickness = 0.8.dp,
                        color = Color(0xFFF3ECE9)
                    )
                }
            }
        }
    }
}