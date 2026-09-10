# Bloom — PCOS Wellness Tracker (Android)

Bloom is a modern, high-fidelity Android application developed using **Jetpack Compose** and **Material 3**. The app delivers a personalized experience for tracking PCOS symptoms and daily wellness targets. It features a persistent onboarding experience, custom components matching Figma specifications, and a policy-compliant Google AdMob integration containing both App Open ads and Adaptive Banners with first-run suppression.

---

## 1. Project Overview & Architecture

The project follows standard Android architectural best practices adhering to **Clean Architecture & MVVM**:

* **UI Layer (Jetpack Compose & Material 3)**: Unidirectional Data Flow (UDF) using state holders, reusable design primitives, custom typography, animations, and non-blocking layout containers.
* **State & Persistence Layer (Jetpack DataStore Preferences)**: Thread-safe, asynchronous key-value persistence for onboarding progression and session eligibility counters.
* **Ad & Monetization Layer (Google Mobile Ads SDK)**: Encapsulated managers for App Open and Adaptive Banner ads decoupled from core business and UI rendering logic.
* **Lifecycle Awareness**: Uses `ProcessLifecycleOwner` and Compose `DisposableEffect` to manage transitions between background, foreground, active views, and native memory release.

```text
com.example.boomapp/
├── ads/
│   ├── AdaptiveAdmobBanner.kt    # Responsive adaptive banner with dismiss & auto-collapse
│   └── AppOpenAdManager.kt       # ProcessLifecycle-aware App Open ad preloader & presenter
├── analytics/
│   └── AdAnalytics.kt            # Centralized event telemetry logger
├── dashboard/
│   ├── HomeDashboard.kt          # Primary dashboard (wellness check-in, cycle metrics)
│   └── components/               # Quick action items, interactive cards, and action buttons
├── data/
│   ├── AdConfig.kt               # Centralized test & production Ad Unit IDs
│   ├── AdPreferences.kt          # Session eligibility storage logic
│   └── OnboardingPreferences.kt  # Onboarding status persistence (DataStore)
├── settingsScreen/
│   └── EditProfileBottomSheet.kt # Material 3 bottom sheet for profile customization
├── welcomeScreen/
│   ├── OnboardingScreen.kt       # HorizontalPager-driven 4-step onboarding
│   └── StepProgressBar.kt       # Custom visual progress bar indicator
├── BoomApplication.kt            # Application subclass managing app-level ad lifecycles
└── MainActivity.kt               # Single Activity router resolving initial navigation

[First Launch Ever]
App Launch ──► DataStore check (isCompleted == false)
            ├──► Suppress App Open Ad (No interruption)
            ├──► Render Onboarding Screen
            └──► User Completes Onboarding ──► Enter Home Screen (Banner is HIDDEN)

[App Killed & Re-Opened]
Subsequent Launch ──► DataStore check (hasCompletedBefore == true)
                   ├──► Show App Open Ad (if preloaded & available)
                   ├──► Navigate directly to Home Screen
                   └──► Home Screen mounts ──► Banner Ad is LOADED & VISIBLE

ALSO HANDLE THIS SCENERIO

No Internet Connection

The Situation: When a device loses internet connectivity or switches into airplane mode, the AdMob SDK cannot communicate with Google servers to download ad creatives or metadata.

How It Is Handled: The app hooks directly into the failure listeners (onAdFailedToLoad) for both banner and App Open ads. Instead of crashing, freezing the screen, or showing a broken placeholder, the app treats the failure silently. The banner container collapses away so no empty white gap appears in the UI, and the App Open ad simply bypasses display, letting the user enter the app immediately without waiting.

AdMob Code 3 (No Fill / Low Inventory)

The Situation: Google servers successfully receive the request, but have no matching ad inventory available to serve to that specific unit at that moment.

How It Is Handled: The composable architecture uses state flags to control visibility. The banner layout does not pre-allocate or reserve fixed screen height on speculation. If a No Fill response arrives, the view never attempts to expand or render, completely preventing awkward blank spaces, misaligned padding, or shifted layouts on the dashboard.

Rapid Backgrounding & App Switching

The Situation: The user might trigger an app open, but immediately swipe up to switch apps or return to the home screen while the full-screen ad is preparing to pop up. In normal setups, displaying a dialog on an activity that is tearing down causes a fatal window token crash (BadTokenException).

How It Is Handled: The ad manager performs an explicit lifecycle safety check right before calling the show command. It verifies whether the underlying activity is currently in a valid, foregrounded state, and ensures it is neither finishing nor destroyed. If the activity is unattached or exiting, the ad show operation is safely skipped.

Multiple Foreground Events

The Situation: If a user rapidly opens, minimizes, and re-opens the app via multitasking, multiple lifecycle triggers fire in quick succession. This can lead to duplicate concurrent network calls or multiple full-screen ads attempting to stack over each other.

How It Is Handled: The manager acts like a traffic controller using boolean gatekeeper flags. If an ad is currently loading, new requests are blocked until that cycle finishes. If an ad is actively displayed on-screen, subsequent foreground events ignore display attempts. The system only triggers the next preload once the active ad is fully dismissed.

Native Resource Leaks (Embedded AdViews)

The Situation: AdMob banner views use heavy, native Android WebViews under the hood. In Jetpack Compose, navigating across screens or triggering frequent recompositions can abandon old views in memory if they are not explicitly cleaned up, causing memory bloat and background battery drain.

How It Is Handled: The banner view is bound to Compose lifecycle effects. The moment the banner leaves the screen hierarchy or is unmounted, its teardown callback triggers, completely destroying the native AdView instance, halting internal background render loops, and clearing WebView resources from device memory.
