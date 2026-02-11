package com.shadowarm.admodule.ads.appopen

import com.google.android.gms.ads.AdError

interface AppOpenAdShowCallback {
    fun onShown(tag: String)
    fun onDismissed(tag: String)
    fun onFailedToShow(tag: String, error: AdError)
}
