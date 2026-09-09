package com.example.boomapp.data

data class HabitItemData(
    val id: Int,
    val iconRes: Int,
    val title: String,
    val subtitle: String,
    val isEnabled: Boolean = true
)
