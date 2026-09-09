package com.example.boomapp.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R
import com.example.boomapp.dialog.ReminderTimeBottomSheet

// Color Palette
private val ScreenBackground = Color(0xFFFAF7F2)
private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val CardSoftPink = Color(0xFFFBF1EE)
private val SwitchThumbColor = Color.White
private val SwitchTrackActive = Color(0xFF8B4D3E)
private val SwitchTrackInactive = Color(0xFFE2D6CF)

data class TrackItem(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val badgeBg: Color,
    val initialChecked: Boolean = true
)

data class GoalTargetItem(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val badgeBg: Color,
    val count: Int,
    val unit: String
)

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    userName: String = "Pankaj",
    onSignOutClick: () -> Unit = {}
) {
    var showReminderSheet by remember { mutableStateOf(false) }
    var reminderTime by remember { mutableStateOf("8:00 AM") }
    var notificationsEnabled by remember { mutableStateOf(true) }

    var mealsCount by remember { mutableIntStateOf(3) }
    var movementCount by remember { mutableIntStateOf(30) }
    var waterCount by remember { mutableIntStateOf(8) }
    var relaxationCount by remember { mutableIntStateOf(15) }
    var sleepCount by remember { mutableIntStateOf(8) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. Header ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "Settings",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = TextDark
                )
                Text(
                    text = "Your profile and daily goal targets",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // --- 2. Profile Card ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(CardSoftPink),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_person),
                                contentDescription = "Avatar",
                                tint = MaroonBrown,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = userName,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "PCOS journey · Not set",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        thickness = 0.6.dp,
                        color = Color(0xFFF1ECE6)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Edit Profile Action */ },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit profile",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_right),
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // --- 3. What you're tracking ---
        item {
            SectionHeader(title = "What you're tracking")

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val trackingOptions = listOf(
                        TrackItem(
                            "Meals",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_meal),
                            Color(0xFFA64D43),
                            Color(0xFFF9EAE7)),
                        TrackItem(
                            "Movement",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_movement),
                            Color(0xFF5E8C76),
                            Color(0xFFEAF4EE)),
                        TrackItem("Water",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_water),
                            Color(0xFF4C82A6),
                            Color(0xFFE9F1F7)),
                        TrackItem("Relaxation",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_relex),
                            Color(0xFF7A6B9B),
                            Color(0xFFF1EDF8)),
                        TrackItem("Sleep",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_sleep),
                            Color(0xFF53597D),
                            Color(0xFFEAEBFA)),
                        TrackItem("Cycle",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_period),
                            Color(0xFFA64D43),
                            Color(0xFFF9EAE7)),
                        TrackItem("Symptom check-in",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_smile),
                            Color(0xFFB88E4B),
                            Color(0xFFFDF6E9))
                    )

                    trackingOptions.forEachIndexed { index, item ->
                        var isChecked by remember { mutableStateOf(item.initialChecked) }
                        TrackingSwitchRow(
                            item = item,
                            checked = isChecked,
                            onCheckedChange = { isChecked = it }
                        )
                        if (index < trackingOptions.lastIndex) {
                            HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFF6F2ED))
                        }
                    }
                }
            }
        }

        // --- 4. Daily goal targets ---
        item {
            SectionHeader(title = "Daily goal targets")

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    GoalCounterRow(
                        title = "Meals",
                        count = mealsCount,
                        unit = "meals",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_meal),
                        iconTint = Color(0xFFA64D43),
                        badgeBg = Color(0xFFF9EAE7),
                        onDecrement = { if (mealsCount > 1) mealsCount-- },
                        onIncrement = { mealsCount++ }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFF6F2ED))

                    GoalCounterRow(
                        title = "Movement",
                        count = movementCount,
                        unit = "min",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_movement),
                        iconTint = Color(0xFF5E8C76),
                        badgeBg = Color(0xFFEAF4EE),
                        onDecrement = { if (movementCount > 5) movementCount -= 5 },
                        onIncrement = { movementCount += 5 }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFF6F2ED))

                    GoalCounterRow(
                        title = "Water",
                        count = waterCount,
                        unit = "glasses",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_water),
                        iconTint = Color(0xFF4C82A6),
                        badgeBg = Color(0xFFE9F1F7),
                        onDecrement = { if (waterCount > 1) waterCount-- },
                        onIncrement = { waterCount++ }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFF6F2ED))

                    GoalCounterRow(
                        title = "Relaxation",
                        count = relaxationCount,
                        unit = "min",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_relex),
                        iconTint = Color(0xFF7A6B9B),
                        badgeBg = Color(0xFFF1EDF8),
                        onDecrement = { if (relaxationCount > 5) relaxationCount -= 5 },
                        onIncrement = { relaxationCount += 5 }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFF6F2ED))

                    GoalCounterRow(
                        title = "Sleep",
                        count = sleepCount,
                        unit = "hrs",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_sleep),
                        iconTint = Color(0xFF53597D),
                        badgeBg = Color(0xFFEAEBFA),
                        onDecrement = { if (sleepCount > 1) sleepCount-- },
                        onIncrement = { sleepCount++ }
                    )
                }
            }
        }

        // --- 5. Notifications & App Info Card ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Notifications toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Notifications",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Daily reminders and log",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = SwitchThumbColor,
                                checkedTrackColor = SwitchTrackActive,
                                uncheckedTrackColor = SwitchTrackInactive
                            )
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        thickness = 0.6.dp,
                        color = Color(0xFFF1ECE6)
                    )

                    // Reminder Time Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showReminderSheet = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Reminder time", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                            Text("8:00 AM", fontSize = 11.sp, color = TextMuted)
                        }
                        Icon(painter = painterResource(id = R.drawable.ic_right),
                            contentDescription = null, tint = TextMuted,
                            modifier = Modifier.size(18.dp))
                    }
                    if (showReminderSheet) {
                        ReminderTimeBottomSheet(
                            initialTime = reminderTime,
                            onDismissRequest = { showReminderSheet = false },
                            onSaveTime = { newTime ->
                                reminderTime = newTime
                            }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        thickness = 0.6.dp,
                        color = Color(0xFFF1ECE6)
                    )

                    // Live Notification Preview
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Preview action */ },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Live notification preview", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                            Text("See lock screen updates", fontSize = 11.sp, color = TextMuted)
                        }
                        Icon(painter = painterResource(R.drawable.ic_right),
                            contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        thickness = 0.6.dp,
                        color = Color(0xFFF1ECE6)
                    )

                    // App Version
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("App version", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                        Text("1.0 - Prototype", fontSize = 12.sp, color = TextMuted)
                    }
                }
            }
        }

        // --- 6. Sign out Button ---
        item {
            OutlinedButton(
                onClick = onSignOutClick,
                shape = RoundedCornerShape(20.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE8D3CD))
                ),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Sign out",
                    color = MaroonBrown,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Section Header Sub-composable
@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        color = TextDark,
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    )
}

// Tracking Switch Row
@Composable
private fun TrackingSwitchRow(
    item: TrackItem,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(item.badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = SwitchThumbColor,
                checkedTrackColor = SwitchTrackActive,
                uncheckedTrackColor = SwitchTrackInactive
            )
        )
    }
}

// Goal Target Counter Row (- count unit +)
@Composable
private fun GoalCounterRow(
    title: String,
    count: Int,
    unit: String,
    icon: ImageVector,
    iconTint: Color,
    badgeBg: Color,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
        }

        // Stepper: [-]  count unit  [+]
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFE2D6CF), CircleShape)
                    .clickable { onDecrement() },
                contentAlignment = Alignment.Center
            ) {
                Text("-", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }

            Text(
                text = "$count $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                modifier = Modifier.widthIn(min = 60.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFE2D6CF), CircleShape)
                    .clickable { onIncrement() },
                contentAlignment = Alignment.Center
            ) {
                Text("+", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSetting() {
    SettingsScreen()
}