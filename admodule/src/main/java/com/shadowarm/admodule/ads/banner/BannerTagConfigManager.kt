package com.shadowarm.admodule.ads.banner

class BannerTagConfigManager {

    private val configs = mutableMapOf<String, BannerAdConfig>()

    fun register(config: BannerAdConfig) {
        configs[config.tag] = config
    }

    fun get(tag: String): BannerAdConfig? = configs[tag]
}
