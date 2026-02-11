package com.shadowarm.admodule.ads.remote

import com.shadowarm.admodule.ads.appopen.AppOpenAdConfig
import com.shadowarm.admodule.ads.appopen.AppOpenTagConfigManager
import com.shadowarm.admodule.ads.banner.BannerAdConfig
import com.shadowarm.admodule.ads.banner.BannerTagConfigManager
import com.shadowarm.admodule.ads.datastore.AdsConfigDataStore
import com.shadowarm.admodule.ads.interstitial.InterstitialAdConfig
import com.shadowarm.admodule.ads.interstitial.InterstitialTagConfigManager
import kotlinx.serialization.json.Json

class AdsConfigInitializer(
    private val dataStore: AdsConfigDataStore,
    private val remoteConfig: RemoteConfigManager,

    private val interManager: InterstitialTagConfigManager,
    private val bannerManager: BannerTagConfigManager,
    private val appOpenManager: AppOpenTagConfigManager
) {

    suspend fun init() {

        remoteConfig.fetch()

        dataStore.getAll().forEach { (_, json) ->
            register(json)
        }

        remoteConfig.getAll().forEach { (key, rawJson) ->
            if (!isAdKey(key)) return@forEach

            dataStore.save(key, rawJson)
            register(rawJson)
        }
    }


    private fun isAdKey(key: String): Boolean {
        return key.startsWith("int_") ||
                key.startsWith("cb_") ||
                key.startsWith("ao_")
    }

    private fun register(rawJson: String) {

        decode<InterstitialAdConfig>(rawJson)?.let {
            interManager.register(it)
            return
        }

        decode<BannerAdConfig>(rawJson)?.let {
            bannerManager.register(it)
            return
        }

        decode<AppOpenAdConfig>(rawJson)?.let {
            appOpenManager.register(it)
            return
        }
    }

    private inline fun <reified T> decode(jsonStr: String): T? {
        return runCatching {
            Json { ignoreUnknownKeys = true }
                .decodeFromString<T>(jsonStr)
        }.getOrNull()
    }
}

