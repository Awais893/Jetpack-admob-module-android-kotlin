package com.shadowarm.admodule.ads.appopen

data class AppOpenAdConfig(
    val tag: String,
    val adUnitId: String,
    val enabled: Boolean,
    val minIntervalMillis: Long
)
