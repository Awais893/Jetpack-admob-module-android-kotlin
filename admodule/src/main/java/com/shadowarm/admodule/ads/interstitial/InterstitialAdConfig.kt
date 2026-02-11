package com.shadowarm.admodule.ads.interstitial



data class InterstitialAdConfig(
    val tag: String,

    // Separate ad unit ids
    val preloadAdUnitId: String,
    val loadAndShowAdUnitId: String,

    // Behaviors
    val preloadEnabled: Boolean = false,
    val loadAndShowEnabled: Boolean = false,

    // Delay only for load & show (UX based)
    val loadAndShowDelayMillis: Long = 0L,

    // Frequency control
    val minIntervalBetweenAdsMillis: Long = 0L
)


