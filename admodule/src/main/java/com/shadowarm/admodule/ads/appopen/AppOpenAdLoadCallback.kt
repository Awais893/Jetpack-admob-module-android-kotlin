package com.shadowarm.admodule.ads.appopen

import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

interface AppOpenAdLoadCallback {
    fun onLoaded(tag: String, ad: AppOpenAd)
    fun onFailed(tag: String, error: LoadAdError)
}
