package com.shadowarm.admodule.ads.banner

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import org.koin.compose.getKoin

@Composable
fun BannerAd(
    tag: String,
    bannerAdManager: BannerAdManager = getKoin().get()
) {
    val activity = LocalActivity.current ?: return

    var adView by remember { mutableStateOf<AdView?>(null) }
    var shimmerHeight by remember { mutableStateOf<Int?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    LaunchedEffect(tag) {
        isLoading = true

        val result = bannerAdManager.getBanner(
            activity,
            tag,
            object : BannerAdLoadCallback {
                override fun onLoaded(tag: String) {
                    isLoading = false
                }

                override fun onFailed(tag: String, error: LoadAdError) {
                    isLoading = false
                    enabled = false
                }
            }
        )

        adView = result.first
        shimmerHeight = result.second

        if (adView == null) {
            isLoading = false
            enabled = false
        }
    }

    // ✅ THIS IS THE MAGIC
    DisposableEffect(tag) {
        onDispose {
            bannerAdManager.clear(tag)
        }
    }

    when {
        !enabled -> Unit

        isLoading && shimmerHeight != null ->
            BannerShimmer(heightDp = shimmerHeight!!)

        adView != null ->
            AndroidView(
                factory = { adView!! },
                modifier = Modifier.fillMaxWidth()
            )
    }
}



