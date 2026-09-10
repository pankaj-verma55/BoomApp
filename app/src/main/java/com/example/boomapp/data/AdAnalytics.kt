package com.example.boomapp.data

import android.os.Bundle
import android.util.Log

object AdAnalytics {
    private const val TAG = "AdAnalytics"

    fun logEvent(eventName: String, params: Map<String, Any?> = emptyMap()) {
        Log.d(TAG, "EVENT: $eventName | params: $params")

        // If using Firebase Analytics:
        // val bundle = Bundle().apply {
        //     params.forEach { (k, v) -> putString(k, v?.toString()) }
        // }
        // Firebase.analytics.logEvent(eventName, bundle)
    }
}