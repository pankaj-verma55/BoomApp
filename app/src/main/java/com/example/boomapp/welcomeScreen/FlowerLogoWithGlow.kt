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
    // 1. Soft pinkish-brown tint matching your theme
    val glowColor = Color(0xFFA65851).copy(alpha = 0.22f)

    Box(
        modifier = modifier
            .size(240.dp) // Large area for the ambient glow to spread
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        glowColor,          // Stronger at the center
                        glowColor.copy(alpha = 0.08f), // Soft falloff
                        Color.Transparent   // Fades out completely toward edges
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // 2. The central flower icon
        Image(
            painter = painterResource(id = R.drawable.ic_logo), // Replace with your vector/drawable
            contentDescription = "Bloom Logo",
            modifier = Modifier
        )
    }
}