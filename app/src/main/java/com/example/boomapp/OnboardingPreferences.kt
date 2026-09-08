package com.example.boomapp

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class OnboardingPreferences(private val context: Context) {
    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("is_onboarding_completed")
    }

    // Reads whether onboarding is complete (defaults to false)
    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED_KEY] ?: false
    }

    // Marks onboarding as complete
    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = true
        }
    }
}