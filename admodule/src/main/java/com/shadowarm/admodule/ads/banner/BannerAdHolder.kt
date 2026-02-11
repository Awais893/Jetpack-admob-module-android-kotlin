package com.shadowarm.admodule.ads.banner

import com.google.android.gms.ads.AdView

class BannerAdHolder {
    var adView:  AdView? = null
    var isLoading = false
    var lastLoadedTime = 0L
    var refreshRunnable: Runnable? = null

}
