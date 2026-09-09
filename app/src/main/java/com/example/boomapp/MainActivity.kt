package com.example.boomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.boomapp.dashboard.HomeDashboard
import com.example.boomapp.ui.theme.BoomAppTheme
import com.example.boomapp.welcomeScreen.OnboardingScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        val preferences = OnboardingPreferences(applicationContext)
        setContent {
            // Collect the completion status; null represents loading
            val isCompleted by preferences.isOnboardingCompleted.collectAsState(initial = null)
            val scope = rememberCoroutineScope()
            when (isCompleted) {
                null -> {
                    // Prevents a sudden white flicker while DataStore initializes
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFAF7F2))
                    )
                }

                false -> {
                    // First time User
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