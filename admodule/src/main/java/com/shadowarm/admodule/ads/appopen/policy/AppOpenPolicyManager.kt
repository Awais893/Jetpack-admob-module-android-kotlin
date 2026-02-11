package com.shadowarm.admodule.ads.appopen.policy

class AppOpenPolicyManager {

    @Volatile
    private var config: AppOpenRemoteConfig =
        AppOpenRemoteConfig(
            enabled = true,
            blockedScreens = emptySet()
        )

    fun update(config: AppOpenRemoteConfig) {
        this.config = config
    }

    fun isAppOpenEnabled(): Boolean {
        return config.enabled
    }

    fun isBlockedForScreen(screenTag: String): Boolean {
        return config.blockedScreens.contains(screenTag)
    }
}
