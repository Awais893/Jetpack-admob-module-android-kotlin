package com.shadowarm.admodule.ads.banner

import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.ads.*

class BannerAdLoader {

     fun load(
        tag: String,
        adView: AdView,
        callback: BannerAdLoadCallback
    ) {
        adView.adListener = object : AdListener() {

            override fun onAdLoaded() {
                callback.onLoaded(tag)
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                callback.onFailed(tag, error)
            }
        }

        adView.loadAd(AdRequest.Builder().build())
    }
}
