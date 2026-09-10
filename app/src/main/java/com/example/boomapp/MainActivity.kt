package com.example.boomapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.boomapp.dashboard.HomeDashboard
import com.example.boomapp.data.AdmobBanner
import com.example.boomapp.data.AppOpenAdManager
import com.example.boomapp.data.dataStore.AdPreferences
import com.example.boomapp.ui.theme.BoomAppTheme
import com.example.boomapp.welcomeScreen.OnboardingScreen
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        val preferences = OnboardingPreferences(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(this@MainActivity) {}
        }
        // Check and set session ad state
        lifecycleScope.launch {
            AdPreferences.initSession(applicationContext)
        }
        setContent {
            // Collect the completion status; null represents loading
            val isCompleted by preferences.isOnboardingCompleted.collectAsState(initial = null)
            val scope = rememberCoroutineScope()
            when (isCompleted) {
                null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFAF7F2))
                    )
                }

                false -> {
                    OnboardingScreen(
                        onFinished = {
                            scope.launch {
                                preferences.setOnboardingCompleted()
                            }
                        }
                    )
                }

                true -> {
                    BoomAppTheme {
                        HomeDashboard(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()

        val app = application as BoomApplication
        val preferences = OnboardingPreferences(applicationContext)

        lifecycleScope.launch {
            if (preferences.isOnboardingCompleted.first()) {
                app.appOpenAdManager.showAdIfAvailable(this@MainActivity)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BoomAppTheme {
        Greeting("Android")
    }
}