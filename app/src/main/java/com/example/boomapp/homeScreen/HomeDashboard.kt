package com.example.boomapp.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R

data class BottomNavItem(
    val title: String,
    val iconRes: Int
)

@Composable
fun HomeDashboard(modifier: Modifier = Modifier) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(title = "Dashboard", iconRes = R.drawable.ic_home),
        BottomNavItem(title = "Insights", iconRes = R.drawable.ic_insite),
        BottomNavItem(title = "Learn", iconRes = R.drawable.ic_learn),
        BottomNavItem(title = "Settings", iconRes = R.drawable.ic_setting)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(), // 👈 Uses incoming modifier here ONLY
        containerColor = Color(0xFFFAF7F2),
        topBar = {},
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.title
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(R.color.lightRed),
                            selectedTextColor = colorResource(R.color.lightRed),
                            unselectedIconColor = colorResource(R.color.lightBrown),
                            unselectedTextColor = colorResource(R.color.lightBrown),
                            indicatorColor = colorResource(R.color.lightRed).copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // 👈 Fresh Modifier (capital M)
            contentPadding = innerPadding      // 👈 Ensures items don't overlap with NavigationBar
        ) {
            // Your list items / cards go here
        }
    }
}