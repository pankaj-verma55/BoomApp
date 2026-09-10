package com.example.boomapp.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.boomapp.OnboardingPreferences
import com.example.boomapp.R
import com.example.boomapp.data.AdaptiveAdmobBanner
import com.example.boomapp.data.AdmobBanner

data class BottomNavItem(
    val title: String,
    val iconRes: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeDashboard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferences = remember {
        OnboardingPreferences(context)
    }
    val userName by preferences.userName.collectAsState(initial = "")

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(title = "Dashboard", iconRes = R.drawable.ic_home),
        BottomNavItem(title = "Insights", iconRes = R.drawable.ic_insite),
        BottomNavItem(title = "Learn", iconRes = R.drawable.ic_learn),
        BottomNavItem(title = "Settings", iconRes = R.drawable.ic_setting)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colorResource(R.color.cream),
        topBar = {},
        bottomBar = {
            Column {
                AdaptiveAdmobBanner()
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
                                selectedIconColor = colorResource(R.color.darkBrown),
                                selectedTextColor = colorResource(R.color.darkBrown),
                                unselectedIconColor = colorResource(R.color.lightBrown),
                                unselectedTextColor = colorResource(R.color.lightBrown),
                                indicatorColor = colorResource(R.color.lightRed).copy(alpha = 0.2f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize().background(colorResource(R.color.cream))
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            when (selectedItemIndex) {
                0 -> BloomDashboard(userName = userName)
                1 -> InsightsScreen()
                2 -> LearnScreen()
                3 -> SettingsScreen(userName = userName)
            }
        }
    }
}

