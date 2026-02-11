package com.shadowarm.admodule.ads.banner

data class BannerAdConfig(
    val tag: String,
    val simpleAdUnitId: String?,
    val collapsibleAdUnitId: String?,
    val simpleEnabled: Boolean,
    val collapsibleEnabled: Boolean,
    val adaptive: Boolean,
    val refreshIntervalMillis: Long,
    val bannerHeightDp: Int
)
