package com.example.boomapp.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

// --- Color Palette ---
private val MaroonBrown = Color(0xFF8B4D3E)
private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val CloseBtnBg = Color(0xFFF7E6E2)
private val ArrowBtnBg = Color(0xFFFDF0ED)
private val SoftBlushCard = Color(0xFFFBF1EE)
private val LegendYellowBorder = Color(0xFFC7A167)
private val ButtonMaroon = Color(0xFFA15347)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogCycleBottomSheet(
    onDismissRequest: () -> Unit,
    onDateConfirmed: (LocalDate) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Current displayed month in calendar view
    var currentYearMonth by remember { mutableStateOf(YearMonth.of(2026, 7)) }

    // Selected last period start date (defaults to July 30, 2026 as shown in design)
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.of(2026, 7, 30)) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .width(42.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE2B7AE))
            )
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp)
        ) {
            // --- Title and Close Button ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Cycle",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaroonBrown
                )

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(CloseBtnBg)
                        .clickable { onDismissRequest() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(com.example.boomapp.R.drawable.ic_cross),
                        contentDescription = "Close",
                        tint = MaroonBrown,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = "When did your last period start?",
                fontSize = 13.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 6.dp, bottom = 14.dp)
            )

            // --- Custom Calendar Card ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3EBE7)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    // Month Selector Header (< July 2026 >)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(ArrowBtnBg)
                                .clickable { currentYearMonth = currentYearMonth.minusMonths(1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.example.boomapp.R.drawable.ic_left),
                                contentDescription = "Previous Month",
                                tint = MaroonBrown,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
                        Text(
                            text = currentYearMonth.format(formatter),
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = MaroonBrown
                        )

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(ArrowBtnBg)
                                .clickable { currentYearMonth = currentYearMonth.plusMonths(1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.example.boomapp.R.drawable.ic_right),
                                contentDescription = "Next Month",
                                tint = MaroonBrown,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Days of week initials: M T W T F S S
                    val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        weekDays.forEach { day ->
                            Text(
                                text = day,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Calendar Grid calculation
                    val daysInMonth = currentYearMonth.lengthOfMonth()
                    val firstDayOfMonth = currentYearMonth.atDay(1).dayOfWeek.value // 1 = Mon, 7 = Sun
                    val emptyPrefixCells = firstDayOfMonth - 1
                    val totalCells = emptyPrefixCells + daysInMonth

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        userScrollEnabled = false
                    ) {
                        items(totalCells) { index ->
                            if (index < emptyPrefixCells) {
                                Spacer(modifier = Modifier.size(36.dp))
                            } else {
                                val dayNumber = index - emptyPrefixCells + 1
                                val date = currentYearMonth.atDay(dayNumber)
                                val isSelected = selectedDate == date

                                Column(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable { selectedDate = date },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "$dayNumber",
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaroonBrown else TextDark
                                    )
                                    if (isSelected) {
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
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Legends Row ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(MaroonBrown)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Last period start", fontSize = 10.sp, color = TextMuted)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .border(1.dp, LegendYellowBorder, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Next expected", fontSize = 10.sp, color = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // --- Log Your Last Period Information Card ---
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SoftBlushCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(com.example.boomapp.R.drawable.ic_period),
                            contentDescription = null,
                            tint = TextDark,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Log your last period",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tap a date on the calendar above and Bloom will estimate your next period and let you know if it looks delayed.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- Done Button ---
            Button(
                onClick = {
                    selectedDate?.let(onDateConfirmed)
                    onDismissRequest()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonMaroon),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Done",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}