package com.shadowarm.admodule.ads.banner

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class BannerAdManager(
    private val configManager: BannerTagConfigManager,
    private val loader: BannerAdLoader
) {

    private val holders = mutableMapOf<String, BannerAdHolder>()
    private val handler = Handler(Looper.getMainLooper())

    fun getBanner(
        activity: Activity,
        tag: String,
        callback: BannerAdLoadCallback
    ): Pair<AdView?, Int?> {

        val config = configManager.get(tag) ?: return Pair(null, null)

        val adUnitId = when {
            config.collapsibleEnabled &&
                    !config.collapsibleAdUnitId.isNullOrBlank() ->
                config.collapsibleAdUnitId

            config.simpleEnabled &&
                    !config.simpleAdUnitId.isNullOrBlank() ->
                config.simpleAdUnitId

            else -> null
        } ?: return Pair(null, null)

        val holder = holders.getOrPut(tag) { BannerAdHolder() }

        val shouldRefresh =
            holder.adView == null ||
                    System.currentTimeMillis() - holder.lastLoadedTime > config.refreshIntervalMillis

        if (!shouldRefresh && holder.adView != null) {
            return Pair(holder.adView, config.bannerHeightDp)
        }

        val adView = AdView(activity).apply {
            this.adUnitId = adUnitId
            setAdSize(
                BannerSizeResolver.resolve(activity, config.adaptive)
            )
        }

        holder.isLoading = true
        holder.adView = adView

        loader.load(
            tag,
            adView,
            object : BannerAdLoadCallback {

                override fun onLoaded(tag: String) {
                    holder.isLoading = false
                    holder.lastLoadedTime = System.currentTimeMillis()
                    callback.onLoaded(tag)
                    scheduleRefresh(activity, tag)
                }

                override fun onFailed(tag: String, error: LoadAdError) {
                    holder.isLoading = false
                    callback.onFailed(tag, error)
                }
            }
        )

        return Pair(adView, config.bannerHeightDp)
    }

    private fun scheduleRefresh(activity: Activity, tag: String) {
        val config = configManager.get(tag) ?: return
        if (config.refreshIntervalMillis <= 0) return

        val holder = holders[tag] ?: return

        // ❌ cancel old
        holder.refreshRunnable?.let { handler.removeCallbacks(it) }

        val runnable = Runnable {
            holders.remove(tag)
            getBanner(activity, tag, object : BannerAdLoadCallback {
                override fun onLoaded(tag: String) {}
                override fun onFailed(tag: String, error: LoadAdError) {}
            })
        }

        holder.refreshRunnable = runnable
        handler.postDelayed(runnable, config.refreshIntervalMillis)
    }

    // ✅ IMPORTANT
    fun clear(tag: String) {
        val holder = holders.remove(tag) ?: return
        holder.refreshRunnable?.let { handler.removeCallbacks(it) }
        holder.refreshRunnable = null
    }

}



