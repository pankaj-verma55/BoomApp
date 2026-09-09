package com.example.boomapp.data

import com.example.boomapp.BuildConfig

//import com.example.boomapp.BuildConfig

object AdConfig {
//    AdMob app ID
//    ca-app-pub-3673493958204954~1688840357
//    AdMob ad unit ID
//    ca-app-pub-3673493958204954/3785510339
    // Official Google Test Ad Unit ID for Banners
    private const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"

    // Replace with your real production Ad Unit ID from AdMob Console
//    unit id
    private const val PROD_BANNER_ID = "ca-app-pub-3673493958204954/3785510339"


    val bannerAdUnitId: String
        get() = if (BuildConfig.DEBUG) TEST_BANNER_ID else PROD_BANNER_ID
}