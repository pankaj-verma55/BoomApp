package com.example.boomapp.welcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StepProgressBar(
    totalSteps: Int = 4,
    currentStep: Int, // 0-indexed (0 to 3)
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFFA65851),
    inactiveColor: Color = Color(0xFFF6E2DE)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalSteps) { stepIndex ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    // Highlights up to the active screen (or use == if only one bar lights up)
                    .background(if (stepIndex <= currentStep) activeColor else inactiveColor)
            )
        }
    }
}