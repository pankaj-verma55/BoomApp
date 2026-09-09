package com.example.boomapp.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R

// Color Theme
private val ScreenBackground = Color(0xFFFAF7F2)
private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val CardSoftPink = Color(0xFFFBF1EE)
private val BarSalmon = Color(0xFFE8BDB5)
private val BarActiveSunday = Color(0xFFA64D43)
private val HeatBarFilled = Color(0xFF8B4D3E)
private val HeatBarEmpty = Color(0xFFF0DCD6)

data class SymptomPatternItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val activeCount: Int
)

@Composable
fun InsightsScreen(
    modifier: Modifier = Modifier
) {
    var isWeekSelected by remember { mutableStateOf(true) }

    val symptomList = listOf(
        SymptomPatternItem("Bloating",
            "Moderate or higher on 5 of 7 days.",
            icon = ImageVector.vectorResource(id = R.drawable.ic_bloating),
            5),
        SymptomPatternItem("Skin & acne",
            "Moderate or higher on 2 of 7 days.",
            icon = ImageVector.vectorResource(id = R.drawable.ic_star_sharp),
            2),
        SymptomPatternItem("Mood",
            "Moderate or higher on 2 of 7 days.",
            icon = ImageVector.vectorResource(id = R.drawable.ic_smile),
            2),
        SymptomPatternItem("Sleep quality",
            "Moderate or higher on 2 of 7 days.",
            icon = ImageVector.vectorResource(id = R.drawable.ic_sleep),
            2)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. Header Title ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "Insights",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = TextDark
                )
                Text(
                    text = "Your week at a glance, and what to try next",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // --- 2. Your Score Card ---
        item {
            SectionHeader(title = "Your score")

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header inside card: 60% and Week/Month Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "60%",
                                fontSize = 28.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = MaroonBrown
                            )
                            Text(
                                text = "week average",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }

                        // Toggle Pill
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFF9EFEB))
                                .padding(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isWeekSelected) MaroonBrown else Color.Transparent)
                                    .clickable { isWeekSelected = true }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Week",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isWeekSelected) Color.White else TextMuted
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (!isWeekSelected) MaroonBrown else Color.Transparent)
                                    .clickable { isWeekSelected = false }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Month",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (!isWeekSelected) Color.White else TextMuted
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Bar Chart
                    val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
                    val barHeights = listOf(56.dp, 80.dp, 64.dp, 72.dp, 72.dp, 84.dp, 6.dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(95.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        dayLabels.forEachIndexed { index, _ ->
                            val isLastDay = index == 6
                            Box(
                                modifier = Modifier
                                    .width(36.dp)
                                    .height(barHeights[index])
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isLastDay) BarActiveSunday else BarSalmon)
                            )
                        }
                    }

                    // Days row below bars
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        dayLabels.forEachIndexed { index, day ->
                            Column(
                                modifier = Modifier.width(36.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = day,
                                    fontSize = 12.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                                if (index == 6) {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 2.dp)
                                            .size(4.dp)
                                            .clip(CircleShape)
                                            .background(MaroonBrown)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Tap a day to see its summary",
                        fontSize = 11.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // --- 3. Cycle Section ---
        item {
            SectionHeader(title = "Cycle")
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CardSoftPink),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_period),
                                contentDescription = null,
                                tint = MaroonBrown,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "No cycle logged yet",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Log your last period date",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_right),
                        contentDescription = null,
                        tint = TextMuted
                    )
                }
            }
        }

        // --- 4. Daily Summary Section ---
        item {
            SectionHeader(title = "Daily summary")
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaroonBrown),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "0%",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Today",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "Nothing logged yet today — no rush, tap a goal below whenever you're ready.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // --- 5. Symptom Patterns Section ---
        item {
            SectionHeader(title = "Symptom patterns")
        }

        items(symptomList.size) { index ->
            SymptomCard(item = symptomList[index])
        }
    }
}

// Sub-component for Section Titles
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

// Sub-component for Symptom Item Cards with 7 Heat Bars
@Composable
private fun SymptomCard(item: SymptomPatternItem) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSoftPink),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaroonBrown,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            // 7-day mini frequency vertical bars
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(7) { dayIndex ->
                    val isFilled = dayIndex >= (7 - item.activeCount)
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isFilled) HeatBarFilled else HeatBarEmpty)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsightsScreenPreview() {
    InsightsScreen()
}