package com.shadowarm.admodule

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.MobileAds
import com.shadowarm.admodule.ads.consent.ConsentManager
import com.shadowarm.admodule.ads.remote.AdsConfigInitializer
import kotlinx.coroutines.launch
//
class AdsInitializer(
    private val consentManager: ConsentManager,
    private val adsConfigInitializer: AdsConfigInitializer
) {

    fun initialize(
        activity: ComponentActivity,
        onComplete: () -> Unit = {}
    ) {
        consentManager.requestConsent(activity){}
        MobileAds.initialize(activity.applicationContext)
        activity.lifecycleScope.launch {
            adsConfigInitializer.init()
            onComplete()
        }
    }
}

