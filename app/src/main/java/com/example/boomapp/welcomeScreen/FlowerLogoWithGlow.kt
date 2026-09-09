package com.example.boomapp.welcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.boomapp.R

@Composable
fun FlowerLogoWithGlow(
    modifier: Modifier = Modifier
) {
    val glowColor = Color(0xFFA65851).copy(alpha = 0.22f)

    Box(
        modifier = modifier
            .size(240.dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        glowColor,
                        glowColor.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Bloom Logo",
            modifier = Modifier
        )
    }
}