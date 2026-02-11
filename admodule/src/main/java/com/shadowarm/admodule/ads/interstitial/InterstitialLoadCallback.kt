package com.shadowarm.admodule.ads.interstitial

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd

interface InterstitialShowCallback {
    fun onShown(tag: String) {}
    fun onDismissed(tag: String) {}
    fun onFailedToShow(tag: String, error: AdError) {}
    fun onClicked(tag: String) {}
    fun onImpression(tag: String) {}
}

interface InterstitialLoadCallback {
    fun onLoaded(tag: String, ad: InterstitialAd)
    fun onFailed(tag: String, error: LoadAdError)
}
