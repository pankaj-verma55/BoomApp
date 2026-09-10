package com.example.boomapp.data

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.boomapp.R
import com.example.boomapp.data.dataStore.AdPreferences
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdaptiveAdmobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.bannerAdUnitId,
    adSize: AdSize = AdSize.BANNER,
    onUserDismissed: () -> Unit = {}
) {
    // 1. If user is in their first-ever session, render nothing
    if (!AdPreferences.isEligibleForAdsInThisSession) {
        return
    }
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var isDismissed by remember { mutableStateOf(false) }
    var isAdLoaded by remember { mutableStateOf(true) }
    var isAdVisible by remember { mutableStateOf(true) }
    val adWidth = configuration.screenWidthDp
    val adaptiveAdSize = remember(adWidth) {
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }


    val adView = remember(adaptiveAdSize) {
        AdView(context).apply {
            setAdSize(adaptiveAdSize)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    isAdLoaded = false
                    isAdVisible = true
                    AdAnalytics.logEvent("banner_ad_loaded", mapOf("ad_unit" to adUnitId))
                    android.util.Log.d("AdMob-->", "Ad loaded successfully!")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    isAdLoaded = false
                    isAdVisible = true
                    AdAnalytics.logEvent(
                        "banner_ad_failed",
                        mapOf("code" to error.code, "message" to error.message)
                    )
                    if (!isNetworkAvailable(context)) {
                        Toast.makeText(
                            context,
                            "Network unavailable. Please check your internet connection.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Unable to load ad",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    android.util.Log.e(
                        "AdMob-->",
                        "Ad failed: ${error.message} (code ${error.code})"
                    )
                }

                override fun onAdImpression() {
                    AdAnalytics.logEvent("banner_ad_impression", mapOf("ad_unit" to adUnitId))
                }
            }
            loadAd(AdRequest.Builder().build())
        }
    }

    DisposableEffect(adView) {
        onDispose {
            adView.destroy()
        }
    }

    AnimatedVisibility(
        visible = isAdVisible && !isDismissed,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(adSize.height.dp),
            contentAlignment = Alignment.Center
        ) {
            // Loader while AdMob is loading
            if (isAdLoaded) {

                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = colorResource(R.color.darkBrown)
                )

            } else {

                // Show actual AdMob banner
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { adView }
                )
                // Right-side Cancel / Cross Icon
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp, end = 6.dp)
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            isAdVisible = false
                            isDismissed = true
                            AdAnalytics.logEvent(
                                "banner_ad_user_closed",
                                mapOf("ad_unit" to adUnitId)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = "Close Ad",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}