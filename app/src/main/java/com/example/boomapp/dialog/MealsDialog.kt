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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val BorderInactive = Color(0xFFF3DDD7)
private val PillActiveBg = Color(0xFF8B4D3E)
private val CloseButtonBg = Color(0xFFF7E6E2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogMealsBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedBreakfast by remember { mutableStateOf<String?>(null) }
    var selectedLunch by remember { mutableStateOf<String?>(null) }
    var selectedDinner by remember { mutableStateOf<String?>(null) }

    val loggedCount = listOfNotNull(selectedBreakfast, selectedLunch, selectedDinner).size

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
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Meals",
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
                        .background(CloseButtonBg)
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

            Text(
                text = "How would you describe each meal today?",
                fontSize = 13.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 8.dp, bottom = 18.dp)
            )

            MealSelectionSection(
                mealName = "Breakfast",
                selectedOption = selectedBreakfast,
                onOptionSelected = { selectedBreakfast = if (selectedBreakfast == it) null else it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            MealSelectionSection(
                mealName = "Lunch",
                selectedOption = selectedLunch,
                onOptionSelected = { selectedLunch = if (selectedLunch == it) null else it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            MealSelectionSection(
                mealName = "Dinner",
                selectedOption = selectedDinner,
                onOptionSelected = { selectedDinner = if (selectedDinner == it) null else it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "$loggedCount of 3 meals logged today",
                fontSize = 12.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun MealSelectionSection(
    mealName: String,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Light", "Balanced", "Indulgent", "Skipped")

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "• $mealName",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonBrown,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MealOptionPill(
                title = options[0],
                isSelected = selectedOption == options[0],
                onClick = { onOptionSelected(options[0]) },
                modifier = Modifier.weight(1f)
            )
            MealOptionPill(
                title = options[1],
                isSelected = selectedOption == options[1],
                onClick = { onOptionSelected(options[1]) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MealOptionPill(
                title = options[2],
                isSelected = selectedOption == options[2],
                onClick = { onOptionSelected(options[2]) },
                modifier = Modifier.weight(1f)
            )
            MealOptionPill(
                title = options[3],
                isSelected = selectedOption == options[3],
                onClick = { onOptionSelected(options[3]) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MealOptionPill(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) PillActiveBg else Color.White,
        border = BorderStroke(1.dp, if (isSelected) PillActiveBg else BorderInactive),
        modifier = modifier.height(44.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.White else TextDark
            )
        }
    }
}