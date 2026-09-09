package com.example.boomapp.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R

private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val CloseBtnBg = Color(0xFFF7E6E2)
private val CardBorderColor = Color(0xFFF3DDD7)
private val RadioUnselected = Color(0xFFE2D6CF)
private val ButtonMaroon = Color(0xFFA15347)

data class ReminderOption(
    val id: String,
    val title: String,
    val subtitle: String,
    val timeValue: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTimeBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveTime: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialTime: String = "08:00 AM"
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val options = listOf(
        ReminderOption("morning", "Morning", "7:30 AM — start the day on track", "07:30 AM"),
        ReminderOption("afternoon", "Afternoon", "1:00 PM — a midday check-in", "01:00 PM"),
        ReminderOption("evening", "Evening", "8:00 PM — wind down and reflect", "08:00 PM")
    )

    var selectedOptionId by remember { mutableStateOf<String?>(null) }
    var customTime by remember { mutableStateOf(initialTime) }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reminder Time",
                    fontSize = 28.sp,
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
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = "Close",
                        tint = MaroonBrown,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = "When should Bloom nudge you to log your day?",
                fontSize = 13.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 6.dp, bottom = 18.dp)
            )

            options.forEach { option ->
                val isSelected = selectedOptionId == option.id

                Surface(
                    onClick = {
                        selectedOptionId = option.id
                        customTime = option.timeValue
                    },
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, if (isSelected) MaroonBrown else CardBorderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.5.dp,
                                    color = if (isSelected) MaroonBrown else RadioUnselected,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(MaroonBrown)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = option.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = option.subtitle,
                                fontSize = 11.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Or set a custom time",
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Surface(
                onClick = {
                    selectedOptionId = null
                },
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = BorderStroke(1.dp, CardBorderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = customTime,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_time_black),
                        contentDescription = "Clock",
                        tint = TextDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSaveTime(customTime)
                    onDismissRequest()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonMaroon),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}