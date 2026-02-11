package com.shadowarm.admodule.ads.appopen

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdLoader(
    private val context: Context
) {

    fun load(
        tag: String,
        adUnitId: String,
        callback: AppOpenAdLoadCallback
    ) {
        AppOpenAd.load(
            context,
            adUnitId,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    callback.onLoaded(tag, ad)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    callback.onFailed(tag, error)
                }
            }
        )
    }
}
