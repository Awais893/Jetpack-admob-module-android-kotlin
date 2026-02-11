package com.shadowarm.admodule.ads.banner

import com.google.android.gms.ads.LoadAdError

interface BannerAdLoadCallback {
    fun onLoaded(tag: String)
    fun onFailed(tag: String, error: LoadAdError)
}
