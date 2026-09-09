package com.example.boomapp.data

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.boomapp.OnboardingPreferences
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.bannerAdUnitId,
    adSize: AdSize = AdSize.BANNER
) {
    val context = LocalContext.current
    val appPreferences = remember { OnboardingPreferences(context) }

    // Read the DataStore value; defaults to false until loaded
    val canShowAds by appPreferences.isOnboardingCompleted.collectAsState(initial = false)

    // If it's the first time user, don't load or render the banner at all
    if (!canShowAds) {
        return
    }
    var isAdVisible by remember { mutableStateOf(true) }
    val adView = remember {
        AdView(context).apply {
            setAdSize(adSize)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    android.util.Log.d("AdMob-->", "Ad loaded successfully!")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    android.util.Log.e("AdMob-->", "Ad failed: ${error.message} (code ${error.code})")
                }
            }
            loadAd(AdRequest.Builder().build())
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            adView.destroy()
        }
    }

    AnimatedVisibility(
        visible = isAdVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(adSize.height.dp),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { adView }
            )
        }
    }
}