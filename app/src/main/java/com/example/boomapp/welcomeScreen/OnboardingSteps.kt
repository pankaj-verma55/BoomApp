package com.example.boomapp.welcomeScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingStepOne() {
    // UI from your screenshot (Flower icon + Welcome text)
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Text(text = "🌸", fontSize = 72.sp)
        FlowerLogoWithGlow()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Welcome to Bloom",
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C1810),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "A simple daily companion for managing PCOS — track your cycle, meals, movement, water, relaxation and sleep.",
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xFF7A6E68),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingStepTwo() {
    // Example: A completely different UI (e.g. selection chips / questionnaire)
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text("What are your primary goals?", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        // Add checkboxes, interactive chips, or custom cards here
        Text("• Regulate cycle\n• Manage acne & symptoms\n• Optimize nutrition", fontSize = 18.sp)
    }
}

@Composable
fun OnboardingStepThree() {
    // Example: A different UI layout (e.g. cards or illustration banner)
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Your custom layout 3
        Text("Personalized Insights", fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OnboardingStepFour() {
    // Example: Final setup or permissions UI
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Your custom layout 4
        Text("Ready to start your journey?", fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}