# Bloom - PCOS Health & Wellness Tracker

A production-grade Android application developed with **Jetpack Compose** (Material 3) designed for personalized PCOS tracking. The application emphasizes high-fidelity UI design, persistent session management via DataStore, and an end-to-end Google AdMob monetization architecture adhering to Google Play policies.

---

## Key Highlights

* **Precision UI Implementation**: Faithful implementation of custom typography, curved layout surfaces, radial gradient accents, and the modular `EditProfileBottomSheet`.
* **Dynamic Onboarding Architecture**: 4-stage onboarding flow using Jetpack Compose `HorizontalPager` coupled with a reactive step-progress header.
* **Persistent Session & State Management**: Jetpack DataStore (Preferences) handles onboarding completion, avoiding UI flicker through null-state placeholders.
* **Full-Lifecycle AdMob Integration**:
  * **Adaptive Banner Ads**: Dynamically anchored to device width (`getCurrentOrientationAnchoredAdaptiveBannerAdSize`) to eliminate layout reflows.
  * **App Open Ads**: Managed via Android's `ProcessLifecycleOwner` to preload and display ads upon application foregrounding.
  * **Session-Aware Suppression**: Suppresses banner impressions throughout the user's initial app run; impressions unlock only on subsequent cold starts.
  * **User Dismissal Support**: An overlay close trigger that collapses the ad footprint cleanly via `AnimatedVisibility`.
  * **Safe Resource Management**: Native `AdView` disposal inside `DisposableEffect` to mitigate background memory leaks.
* **Granular Event Telemetry**: Complete logging implementation across all ad states:
  * App Open: `app_open_ad_requested`, `app_open_ad_loaded`, `app_open_ad_failed`, `app_open_ad_shown`, `app_open_ad_dismissed`
  * Banner: `banner_ad_loaded`, `banner_ad_failed`, `banner_ad_impression`, `banner_ad_user_closed`

---

## Tech Stack & Architecture

* **UI Toolkit**: Jetpack Compose (Material 3)
* **Language & Concurrency**: Kotlin, Coroutines, StateFlow / Flow
* **Local Storage**: Jetpack DataStore (Preferences)
* **Monetization**: Google Mobile Ads SDK (`com.google.android.gms:play-services-ads`)
* **Architecture Pattern**: MVVM / Clean Architecture separating UI components, DataStore repositories, and monetization wrappers.

---

## Directory Overview

```text
com.example.boomapp/
├── ads/
│   ├── AdaptiveAdmobBanner.kt    # Responsive banner composable with dismiss & animation state
│   └── AppOpenAdManager.kt       # ProcessLifecycle observer for cached App Open delivery
├── analytics/
│   └── AdAnalytics.kt            # Centralized telemetry logger
├── dashboard/
│   └── HomeDashboard.kt          # Primary post-onboarding landing screen
├── data/
│   ├── AdConfig.kt               # Centralized test/prod unit configuration
│   ├── AdPreferences.kt          # Session eligibility storage logic
│   └── OnboardingPreferences.kt  # Onboarding completion DataStore implementation
├── settingsScreen/
│   └── EditProfileBottomSheet.kt # Material 3 bottom sheet for profile customization
├── welcomeScreen/
│   ├── OnboardingScreen.kt       # HorizontalPager-driven onboarding experience
│   └── StepProgressBar.kt       # Custom visual progress bar
├── BoomApplication.kt            # Base Application class managing ad lifecycle hooks
└── MainActivity.kt               # Entry point resolving routing states
