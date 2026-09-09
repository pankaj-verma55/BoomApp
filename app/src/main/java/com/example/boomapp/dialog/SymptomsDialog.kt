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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val CloseBtnBg = Color(0xFFF7E6E2)
private val PillBorderInactive = Color(0xFFF3DDD7)
private val PillActiveBg = Color(0xFF8B4D3E)
private val ButtonMaroon = Color(0xFFA15347)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogSymptomsBottomSheet(
    onDismissRequest: () -> Unit,
    onDoneClick: (Map<String, String>) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedBloating by remember { mutableStateOf<String?>(null) }
    var selectedSkin by remember { mutableStateOf<String?>(null) }
    var selectedMood by remember { mutableStateOf<String?>(null) }
    var selectedSleep by remember { mutableStateOf<String?>(null) }

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
                    text = "Today's symptoms",
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
                        painter = painterResource(com.example.boomapp.R.drawable.ic_cross),
                        contentDescription = "Close",
                        tint = MaroonBrown,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = "These are separate from your daily goals — logging them just helps you (and eventually your doctor) spot patterns over time.",
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
            )

            SymptomRowSection(
                title = "Bloating",
                icon = painterResource(com.example.boomapp.R.drawable.ic_bloating),
                options = listOf("None", "Mild", "Moderate", "Severe"),
                selectedOption = selectedBloating,
                onOptionSelected = { selectedBloating = if (selectedBloating == it) null else it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SymptomRowSection(
                title = "Skin & acne",
                icon = painterResource(com.example.boomapp.R.drawable.ic_star_sharp),
                options = listOf("Clear", "Mild", "Moderate", "Flare-up"),
                selectedOption = selectedSkin,
                onOptionSelected = { selectedSkin = if (selectedSkin == it) null else it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SymptomRowSection(
                title = "Mood",
                icon = painterResource(com.example.boomapp.R.drawable.ic_mood),
                options = listOf("Great", "Okay", "Low", "Very low"),
                selectedOption = selectedMood,
                onOptionSelected = { selectedMood = if (selectedMood == it) null else it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SymptomRowSection(
                title = "Sleep quality",
                icon = painterResource(com.example.boomapp.R.drawable.ic_sleep),
                options = listOf("Great", "Okay", "Poor", "Very poor"),
                selectedOption = selectedSleep,
                onOptionSelected = { selectedSleep = if (selectedSleep == it) null else it }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    val results = buildMap {
                        selectedBloating?.let { put("bloating", it) }
                        selectedSkin?.let { put("skin", it) }
                        selectedMood?.let { put("mood", it) }
                        selectedSleep?.let { put("sleep", it) }
                    }
                    onDoneClick(results)
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

@Composable
private fun SymptomRowSection(
    title: String,
    icon: Painter,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaroonBrown,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaroonBrown
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedOption == option
                Surface(
                    onClick = { onOptionSelected(option) },
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) PillActiveBg else Color.White,
                    border = BorderStroke(1.dp, if (isSelected) PillActiveBg else PillBorderInactive),
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else TextDark
                        )
                    }
                }
            }
        }
    }
}