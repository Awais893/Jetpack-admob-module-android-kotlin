package com.shadowarm.admodule.ads.appopen.policy

data class AppOpenRemoteConfig(
    val enabled: Boolean,
    val blockedScreens: Set<String>
)
