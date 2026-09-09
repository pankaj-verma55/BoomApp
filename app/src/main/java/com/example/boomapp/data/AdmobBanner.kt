package com.example.boomapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.boomapp.OnboardingPreferences
import com.example.boomapp.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.bannerAdUnitId,
    adSize: AdSize = AdSize.BANNER
) {
    val context = LocalContext.current
    val appPreferences = remember { OnboardingPreferences(context) }

    val canShowAds by appPreferences.isOnboardingCompleted.collectAsState(initial = false)

    if (!canShowAds) {
        return
    }
    var isLoading by remember { mutableStateOf(true) }
    var isAdVisible by remember { mutableStateOf(true) }
    val adView = remember {
        AdView(context).apply {
            setAdSize(adSize)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    isLoading = false
                    isAdVisible = true
                    android.util.Log.d("AdMob-->", "Ad loaded successfully!")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    isLoading = false
                    isAdVisible = false
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
            // Loader while AdMob is loading
            if (isLoading) {

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
            }
//            AndroidView(
//                modifier = Modifier.fillMaxWidth(),
//                factory = { adView }
//            )
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false

    val capabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
    )
}