package com.shadowarm.admodule.ads.interstitial

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdLoader(
    private val context: Context
) {

    fun load(
        tag: String,
        adUnitId: String,
        callback: InterstitialLoadCallback
    ) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    callback.onLoaded(tag, ad)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    callback.onFailed(tag, error)
                }
            }
        )
    }
}
