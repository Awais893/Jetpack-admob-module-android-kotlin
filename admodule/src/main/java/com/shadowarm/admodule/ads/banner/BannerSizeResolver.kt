package com.shadowarm.admodule.ads.banner

import android.app.Activity
import com.google.android.gms.ads.AdSize

object BannerSizeResolver {

    fun resolve(
        activity: Activity,
        adaptive: Boolean
    ): AdSize {
        return if (adaptive) {
            val metrics = activity.resources.displayMetrics
            val adWidth = (metrics.widthPixels / metrics.density).toInt()
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                activity,
                adWidth
            )
        } else {
            AdSize.BANNER
        }
    }
}
