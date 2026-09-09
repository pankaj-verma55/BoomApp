package com.example.boomapp.welcomeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.OnboardingPreferences
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val totalPages = 4
    val pagerState = rememberPagerState(pageCount = { totalPages })
    val scope = rememberCoroutineScope()
    val primaryBrown = Color(0xFFA65851)
    val lightCreamBackground = Color(0xFFFBF8F5)
    val context = LocalContext.current
    val preferences = remember {
        OnboardingPreferences(context)
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = lightCreamBackground,
        topBar = {
            StepProgressBar(
                totalSteps = totalPages,
                currentStep = pagerState.currentPage,
                activeColor = primaryBrown,
                inactiveColor = Color(0xFFF3E2DE),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding( bottom = 8.dp)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 8.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < totalPages - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            scope.launch {
                                // Save name
                                preferences.saveUserName(name)
                                preferences.setOnboardingCompleted()
                                onFinished()
                            }
//                            onFinished()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBrown),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = when (pagerState.currentPage) {
                                0 -> "Get started"
                                totalPages-1 -> "Enter Bloom"
                                else -> "Continue"
                            },
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { pageIndex ->
            when (pageIndex) {
                0 -> OnboardingStepOne()
                1 -> OnboardingStepTwo(name, onNameChange = {name = it})
                2 -> OnboardingStepThree()
                3 -> OnboardingStepFour()
            }
        }
    }
}