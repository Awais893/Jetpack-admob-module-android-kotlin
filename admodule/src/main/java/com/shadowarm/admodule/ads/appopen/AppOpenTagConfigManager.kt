package com.shadowarm.admodule.ads.appopen

class AppOpenTagConfigManager {

    private val configs = mutableMapOf<String, AppOpenAdConfig>()

    fun register(config: AppOpenAdConfig) {
        configs[config.tag] = config
    }

    fun get(tag: String): AppOpenAdConfig? = configs[tag]

    fun clearAll() {
        configs.clear()
    }
}
