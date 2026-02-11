package com.shadowarm.admodule.ads.interstitial

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.shadowarm.admodule.ads.appopen.policy.AdDisplayController.resumeAppOpen
import com.shadowarm.admodule.ads.appopen.policy.AdDisplayController.suppressAppOpen
import com.shadowarm.admodule.utils.dialogs.LoadingDialogProvider
import java.util.UUID

class InterstitialAdManager(
    private val loader: InterstitialAdLoader,
    private val tagConfigManager: InterstitialTagConfigManager,
    private val loadingDialogProvider: LoadingDialogProvider
) {

    private val holders = mutableMapOf<String, InterstitialAdHolder>()
    private val handler = Handler(Looper.getMainLooper())

    // active loadAndShow request per tag
    private val activeRequestIds = mutableMapOf<String, String>()

    // ---------- PRELOAD (TAG ONLY) ----------
    fun preload(tag: String) {
        val config = tagConfigManager.get(tag) ?: return
        if (!config.preloadEnabled) return

        loadInternal(
            tag = tag,
            adUnitId = config.preloadAdUnitId,
            allowIfHasAd = false,
            requestId = null
        )
    }

    // ---------- LOAD & SHOW (TAG ONLY) ----------
    fun loadAndShow(
        activity: Activity,
        tag: String,
        callback: InterstitialShowCallback? = null
    ) {
        val config = tagConfigManager.get(tag)
        if (config == null || !config.loadAndShowEnabled) {
            callback?.onDismissed(tag)
            return
        }

        val holder = holderFor(tag)

        // CASE 1: already loaded → show after delay
        holder.ad?.let { ad ->
            handler.postDelayed(
                { showInternal(activity, tag, ad, callback) },
                config.loadAndShowDelayMillis
            )
            return
        }

        // CASE 2: not loaded → start a timed request
        val requestId = UUID.randomUUID().toString()
        activeRequestIds[tag] = requestId

        val dialog: Dialog = loadingDialogProvider.show(activity)

        loadInternal(
            tag = tag,
            adUnitId = config.loadAndShowAdUnitId,
            allowIfHasAd = true,
            requestId = requestId
        )

        val timeout = config.minIntervalBetweenAdsMillis
        if (timeout <= 0L) {
            dialog.dismiss()
            activeRequestIds.remove(tag)
            callback?.onDismissed(tag)
            return
        }

        // timeout watcher: invalidate request ONLY (do not clear cached ad)
        handler.postDelayed(
            {
                if (activeRequestIds[tag] == requestId) {
                    activeRequestIds.remove(tag)
                    dialog.dismiss()
                    callback?.onDismissed(tag)
                }
            },
            timeout
        )
    }

    // ---------- INTERNAL LOAD ----------
    private fun loadInternal(
        tag: String,
        adUnitId: String,
        allowIfHasAd: Boolean,
        requestId: String?
    ) {
        val holder = holderFor(tag)

        if (holder.isLoading) return
        if (!allowIfHasAd && holder.ad != null) return

        holder.isLoading = true

        loader.load(
            tag,
            adUnitId,
            object : InterstitialLoadCallback {

                override fun onLoaded(tag: String, ad: InterstitialAd) {
                    val h = holderFor(tag)
                    h.isLoading = false
                    h.ad = ad // always cache

                    // show ONLY if this request is still active
                    if (requestId != null && activeRequestIds[tag] == requestId) {
                        // do nothing here; show happens from loadAndShow flow
                        // (we intentionally avoid auto-show to keep control centralized)
                    }
                }

                override fun onFailed(tag: String, error: LoadAdError) {
                    val h = holderFor(tag)
                    h.isLoading = false
                }
            }
        )
    }

    // ---------- INTERNAL SHOW ----------
    private fun showInternal(
        activity: Activity,
        tag: String,
        ad: InterstitialAd,
        callback: InterstitialShowCallback?
    ) {
        // this request is being consumed
        activeRequestIds.remove(tag)

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdShowedFullScreenContent() {
                suppressAppOpen()
                callback?.onShown(tag)
            }

            override fun onAdDismissedFullScreenContent() {
                resumeAppOpen()
                callback?.onDismissed(tag)
                clear(tag) // clear ONLY after actual use
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                callback?.onFailedToShow(tag, error)
                callback?.onDismissed(tag)
                clear(tag)
            }

            override fun onAdClicked() {
                callback?.onClicked(tag)
            }

            override fun onAdImpression() {
                suppressAppOpen()
                callback?.onImpression(tag)
            }
        }

        // one-time use safety
        holders[tag]?.ad = null

        try {
            ad.show(activity)
        } catch (_: Throwable) {
            callback?.onDismissed(tag)
            clear(tag)
        }
    }

    // ---------- UTIL ----------
    private fun holderFor(tag: String): InterstitialAdHolder =
        holders.getOrPut(tag) { InterstitialAdHolder() }

    fun clear(tag: String) {
        holders.remove(tag)
        activeRequestIds.remove(tag)
    }

    fun clearAll() {
        holders.clear()
        activeRequestIds.clear()
    }
}
