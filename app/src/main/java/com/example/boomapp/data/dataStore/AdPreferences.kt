package com.example.boomapp.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.adDataStore by preferencesDataStore(name = "ad_preferences")

class AdPreferences(private val context: Context) {
    companion object {
        private val HAS_COMPLETED_FIRST_SESSION = booleanPreferencesKey("has_completed_first_session")

        // In-memory flag for the current app run lifecycle
        var isEligibleForAdsInThisSession: Boolean = false
            private set

        suspend fun initSession(context: Context) {
            val prefs = context.adDataStore.data.first()
            val hasCompletedBefore = prefs[HAS_COMPLETED_FIRST_SESSION] ?: false

            // If they already finished a session previously, enable ads for this session
            isEligibleForAdsInThisSession = hasCompletedBefore

            // If this is their first session ever, mark the flag in DataStore
            // so NEXT time they open the app, it reads true.
            if (!hasCompletedBefore) {
                context.adDataStore.edit { preferences ->
                    preferences[HAS_COMPLETED_FIRST_SESSION] = true
                }
            }
        }
    }
}