package com.example.boomapp.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TrackItem(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val badgeBg: Color,
    val initialChecked: Boolean = true
)
