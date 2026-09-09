package com.example.boomapp.data

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdManager(
    private val context: Context
) {

    private var appOpenAd: AppOpenAd? = null

    private var isLoadingAd = false
    private var isShowingAd = false

    fun loadAd() {

        // Prevent multiple requests
        if (isLoadingAd || appOpenAd != null) {
            return
        }

        isLoadingAd = true

        val request = AdRequest.Builder().build()

        AppOpenAd.load(
            context,
            AdConfig.PROD_BANNER_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    isLoadingAd = false
                    appOpenAd = ad
                    Log.d("AppOpenAd", "Ad loaded")
                }

                override fun onAdFailedToLoad(
                    error: LoadAdError
                ) {
                    isLoadingAd = false
                    appOpenAd = null
                    Log.e(
                        "AppOpenAd",
                        "Ad failed to load: ${error.message}"
                    )
                }
            }
        )
    }

    fun showAdIfAvailable(
        activity: Activity
    ) {

        // Prevent multiple ads simultaneously
        if (isShowingAd) {
            return
        }

        val ad = appOpenAd
        // No ad available
        if (ad == null) {
            loadAd()
            return
        }

        isShowingAd = true

        ad.fullScreenContentCallback =
            object : FullScreenContentCallback() {

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                }

                override fun onAdDismissedFullScreenContent() {

                    isShowingAd = false

                    // Ad can only be shown once
                    appOpenAd = null

                    // Load next ad
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(
                    adError: AdError
                ) {

                    isShowingAd = false
                    appOpenAd = null

                    loadAd()
                }
            }

        ad.show(activity)
    }
}