package com.shadowarm.admodule.ads.appopen

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.shadowarm.admodule.ads.appopen.policy.AdDisplayController
import com.shadowarm.admodule.ads.appopen.policy.AdDisplayController.canShowAppOpenUsingPolicy
import com.shadowarm.admodule.utils.dialogs.LoadingDialogProvider
import java.util.UUID

class AppOpenAdManager(
    private val loader: AppOpenAdLoader,
    private val tagConfigManager: AppOpenTagConfigManager,
    private val loadingDialogProvider: LoadingDialogProvider
) {

    private val holder = AppOpenAdHolder()
    private val handler = Handler(Looper.getMainLooper())
    private var activeRequestId: String? = null

    fun loadAndShow(
        activity: Activity,
        tag: String,
        callback: AppOpenAdShowCallback? = null
    ) {
        val config = tagConfigManager.get(tag)

        if (config == null || !config.enabled) {
            callback?.onDismissed(tag)
            return
        }

        if (!AdDisplayController.canShowAppOpen() || canShowAppOpenUsingPolicy()) {
            callback?.onDismissed(tag)
            return
        }



        val now = System.currentTimeMillis()
        if (now - holder.lastShownTimeMillis < config.minIntervalMillis) {
            callback?.onDismissed(tag)
            return
        }

        // already loaded → show immediately
        holder.ad?.let { ad ->
            showInternal(activity, tag, ad, callback)
            return
        }

        // start loading
        if (holder.isLoading) {
            callback?.onDismissed(tag)
            return
        }

        holder.isLoading = true
        val requestId = UUID.randomUUID().toString()
        activeRequestId = requestId

        val dialog: Dialog = loadingDialogProvider.show(activity)

        loader.load(
            tag,
            config.adUnitId,
            object : AppOpenAdLoadCallback {

                override fun onLoaded(tag: String, ad: AppOpenAd) {
                    holder.isLoading = false
                    holder.ad = ad

                    if (activeRequestId == requestId) {
                        dialog.dismiss()
                        showInternal(activity, tag, ad, callback)
                    }
                }

                override fun onFailed(tag: String, error: LoadAdError) {
                    holder.isLoading = false
                    dialog.dismiss()
                    callback?.onDismissed(tag)
                }
            }
        )
    }

    private fun showInternal(
        activity: Activity,
        tag: String,
        ad: AppOpenAd,
        callback: AppOpenAdShowCallback?
    ) {
        activeRequestId = null
        holder.ad = null

        AdDisplayController.suppressAppOpen()

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdShowedFullScreenContent() {
                holder.lastShownTimeMillis = System.currentTimeMillis()
                callback?.onShown(tag)
            }

            override fun onAdDismissedFullScreenContent() {
                callback?.onDismissed(tag)
                AdDisplayController.resumeAppOpen()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                callback?.onFailedToShow(tag, error)
                callback?.onDismissed(tag)
                AdDisplayController.resumeAppOpen()
            }
        }

        try {
            ad.show(activity)
        } catch (_: Throwable) {
            callback?.onDismissed(tag)
            AdDisplayController.resumeAppOpen()
        }
    }
}
