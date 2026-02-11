package com.shadowarm.admodule.ads.interstitial

import com.google.android.gms.ads.interstitial.InterstitialAd

internal data class InterstitialAdHolder(
    var ad: InterstitialAd? = null,
    var lastShownTimeMillis: Long = 0L,
    var isLoading: Boolean = false
)
