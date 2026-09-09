package com.example.boomapp

import android.app.Application
import com.example.boomapp.data.AppOpenAdManager
import com.google.android.gms.ads.MobileAds

class BoomApplication : Application() {

    lateinit var appOpenAdManager: AppOpenAdManager
        private set

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this)

        appOpenAdManager = AppOpenAdManager(this)

        appOpenAdManager.loadAd()
    }
}