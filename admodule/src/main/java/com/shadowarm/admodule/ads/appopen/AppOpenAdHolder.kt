package com.shadowarm.admodule.ads.appopen

import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdHolder {
    var ad: AppOpenAd? = null
    var isLoading: Boolean = false
    var lastShownTimeMillis: Long = 0L
}
