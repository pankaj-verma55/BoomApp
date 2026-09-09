package com.example.boomapp.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette
private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val SoftBlushBg = Color(0xFFFBF1EE)
private val CloseBtnBg = Color(0xFFF7E6E2)
private val StepperCircleBg = Color(0xFFFDF0ED)
private val StepperIconColor = Color(0xFF8B4D3E)
private val LinkBlue = Color(0xFF3B6CB5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogMovementBottomSheet(
    title:String,
    onDismissRequest: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var movementMinutes by remember { mutableIntStateOf(0) }
    var dialogTitle: String by remember {mutableStateOf("") }
    when (title) {
        "Movement" -> {
            dialogTitle = title
        }
        "Water" -> {
            dialogTitle = title
        }
        "Sleep" -> {
            dialogTitle = title
        }
        "Relaxation" -> {
            dialogTitle = title
        }
        "Meals" -> {
            dialogTitle = title
        }
        else -> {
            // Any other title
        }
    }
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
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Title and Close Button ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log $dialogTitle",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaroonBrown
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(CloseBtnBg)
                        .clickable { onDismissRequest() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(com.example.boomapp.R.drawable.ic_cross),
                        contentDescription = "Close",
                        tint = MaroonBrown,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Subtitle ---
            Text(
                text = "Today's $dialogTitle",
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(18.dp))

            // --- Stepper Row (- 0 min +) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Minus Button
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(StepperCircleBg)
                        .clickable {
                            if (movementMinutes >= 5) movementMinutes -= 5
                            else movementMinutes = 0
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "–",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = StepperIconColor
                    )
                }

                Spacer(modifier = Modifier.width(28.dp))

                // Value Display
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$movementMinutes",
                        fontSize = 36.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = " min",
                        fontSize = 15.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(28.dp))

                // Plus Button
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(StepperCircleBg)
                        .clickable { movementMinutes += 5 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = StepperIconColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Goal & Settings Hint ---
            val goalText = buildAnnotatedString {
                append("Goal: 30 min · adjust in ")
                pushStringAnnotation(tag = "SETTINGS", annotation = "settings")
                withStyle(
                    style = SpanStyle(
                        color = LinkBlue,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Settings")
                }
                pop()
            }

            Text(
                text = goalText,
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.clickable { onNavigateToSettings() }
            )

            Spacer(modifier = Modifier.height(22.dp))

            // --- Recommendation Card ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftBlushBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(com.example.boomapp.R.drawable.ic_movement),
                            contentDescription = null,
                            tint = MaroonBrown,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Get moving today",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "No movement logged yet today. Try a brisk 30-minute walk, a swim, or some light strength training — these are especially good for insulin sensitivity with PCOS.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick "+10 min" increment pill
                    Surface(
                        onClick = { movementMinutes += 10 },
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFF0DCD7)),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        ) {
                            Text(
                                text = "+10 min",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaroonBrown
                            )
                        }
                    }
                }
            }
        }
    }
}