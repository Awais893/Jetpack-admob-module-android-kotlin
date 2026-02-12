# 🚀 ShadowArm Ads Module

A modular, scalable, production-ready Ads SDK for Android built using
Jetpack Compose, Koin, Firebase Remote Config, Kotlin Serialization, and
DataStore.

This module provides:

-   Interstitial Ads\
-   App Open Ads\
-   Simple & Collapsible Banner Ads\
-   Remote-config driven placements\
-   Offline support via DataStore caching\
-   Tag-based placement system\
-   Clean architecture & separation of concerns

------------------------------------------------------------------------

# 🧠 Architecture Overview

Firebase Remote Config\
↓\
RemoteConfigSource\
↓\
RemoteConfigManager\
↓\
AdsConfigDataStore (cached JSON)\
↓\
AdsConfigInitializer\
↓\
TagConfigManagers\
↓\
Ad Managers (Interstitial / Banner / AppOpen)

------------------------------------------------------------------------

# 🎯 Core Concept: Tag-Based Placement

Each ad placement is identified using a unique **tag**.

  Ad Type        Example Tag
  -------------- -------------
  Interstitial   int_splash
  Banner         cb_home
  App Open       ao_language

Rules:

-   UI only passes the `tag`
-   All configuration is resolved internally
-   Remote config JSON must also contain the `tag`
-   Tag represents placement identity

------------------------------------------------------------------------

# 📦 Ad Configuration Models


Rule:

-   Firebase key = placement key\
-   JSON value = config (same field names as Kotlin model)\
-   `tag` must be present inside JSON

------------------------------------------------------------------------

### Example: int_splash

``` json
{
  "tag": "AppLang_next",
  "preloadAdUnitId": "ca-app-pub-xxx/preload",
  "loadAndShowAdUnitId": "ca-app-pub-xxx/show",
  "preloadEnabled": true,
  "loadAndShowEnabled": true,
  "loadAndShowDelayMillis": 400,
  "minIntervalBetweenAdsMillis": 15000
}
```

------------------------------------------------------------------------

# 🗂 Remote Config System

## RemoteConfigSource

``` kotlin
interface RemoteConfigSource {
    fun getString(key: String): String
    fun fetchAndActivate(onComplete: (Boolean) -> Unit)
}
```

------------------------------------------------------------------------

# 💾 DataStore Caching

-   Remote JSON is cached locally\
-   App works offline\
-   First launch loads from DataStore\
-   Remote update overwrites DataStore and updates managers

------------------------------------------------------------------------

# 🧩 Usage Example

``` kotlin
interstitialAdManager.loadAndShow(activity, "int_splash")
```

------------------------------------------------------------------------

# 🏆 Why This Architecture?

-   Remote-driven (no app update required)\
-   Tag-based flexible placement control\
-   Offline support via DataStore\
-   Type-safe JSON parsing\
-   Backend-agnostic design

------------------------------------------------------------------------

License: Internal / Proprietary
