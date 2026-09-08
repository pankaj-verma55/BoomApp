package com.example.boomapp.welcomeScreen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val totalPages = 4
    val pagerState = rememberPagerState(pageCount = { totalPages })
    val scope = rememberCoroutineScope()

    val primaryBrown = Color(0xFFA65851)
    val lightCreamBackground = Color(0xFFFBF8F5)

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
                    .padding(top = 32.dp, bottom = 8.dp)
            )
        },
//        topBar = {
////            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
//            // The 4-bar indicator stays and updates automatically as you swipe
//            StepProgressBar(
//                totalSteps = totalPages,
//                currentStep = pagerState.currentPage,
//                activeColor = primaryBrown,
//                inactiveColor = Color(0xFFF3E2DE),
//                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//            )
//        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 48.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < totalPages - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onFinished()
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
        // 👇 HERE IS THE MAIN CHANGE:
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { pageIndex ->
            when (pageIndex) {
                0 -> OnboardingStepOne()
                1 -> OnboardingStepTwo()
                2 -> OnboardingStepThree()
                3 -> OnboardingStepFour()
            }
        }
    }
}