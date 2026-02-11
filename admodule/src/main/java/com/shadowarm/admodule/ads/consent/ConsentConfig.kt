package com.shadowarm.admodule.ads.consent

data class ConsentConfig(
    val isDebug: Boolean = false,
    val testDeviceHashedIds: List<String> = emptyList()
)

