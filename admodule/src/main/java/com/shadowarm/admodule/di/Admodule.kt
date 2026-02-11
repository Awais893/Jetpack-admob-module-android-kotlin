package com.shadowarm.admodule.di

import android.content.Context
import com.shadowarm.admodule.AdsInitializer
import com.shadowarm.admodule.ads.appopen.AppOpenAdLoader
import com.shadowarm.admodule.ads.appopen.AppOpenAdManager
import com.shadowarm.admodule.ads.appopen.AppOpenTagConfigManager
import com.shadowarm.admodule.ads.appopen.policy.AdDisplayController
import com.shadowarm.admodule.ads.appopen.policy.AppOpenPolicyManager
import com.shadowarm.admodule.ads.banner.BannerAdLoader
import com.shadowarm.admodule.ads.banner.BannerAdManager
import com.shadowarm.admodule.ads.banner.BannerTagConfigManager
import com.shadowarm.admodule.ads.consent.ConsentConfig
import com.shadowarm.admodule.ads.consent.ConsentManager
import com.shadowarm.admodule.ads.interstitial.InterstitialAdLoader
import com.shadowarm.admodule.ads.interstitial.InterstitialAdManager
import com.shadowarm.admodule.ads.interstitial.InterstitialTagConfigManager
import com.shadowarm.admodule.ads.remote.AdsConfigInitializer
import com.shadowarm.admodule.utils.dialogs.LoadingDialogProvider
import com.shadowarm.admodule.utils.dialogs.Material3LoadingDialogProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun adsModule(
    context: Context,
    isDebug: Boolean,
    testDeviceHashedIds: List<String>
) = module {

    single {
        ConsentConfig(
            isDebug = isDebug,
            testDeviceHashedIds = testDeviceHashedIds
        )
    }
    single {
        InterstitialTagConfigManager()
    }
    single {
        ConsentManager(
            context = context,
            config = get()
        )
    }
    single<LoadingDialogProvider> {
        Material3LoadingDialogProvider()
    }
    single {
        InterstitialAdLoader(
            context = androidContext()
        )
    }
    single {
        InterstitialAdManager(
            loader = get(),
            tagConfigManager = get(),
            loadingDialogProvider = get()
        )
    }


    single {
        AppOpenPolicyManager()
    }

    single {
        // init once with policy manager
        AdDisplayController.apply {
            init(get())
        }
    }
    /* ---------------- APP OPEN ---------------- */

    single {
        AppOpenTagConfigManager()
    }

    single {
        AppOpenAdLoader(
            context = androidContext()
        )
    }

    single {
        AdsInitializer(
            consentManager = get(),
            adsConfigInitializer = get()
        )
    }


    single {
        AppOpenAdManager(
            loader = get(),
            tagConfigManager = get(),
            loadingDialogProvider = get()
        )
    }

    /* ---------------- BANNER ---------------- */

    single {
        BannerTagConfigManager()
    }

    single {
        BannerAdLoader()
    }

    single {
        BannerAdManager(
            configManager = get(),
            loader = get()
        )
    }

    single {
        AdsConfigInitializer(
            dataStore = get(),
            remoteConfig = get(),
            interManager = get(),
            bannerManager = get(),
            appOpenManager = get()
        )
    }

}
