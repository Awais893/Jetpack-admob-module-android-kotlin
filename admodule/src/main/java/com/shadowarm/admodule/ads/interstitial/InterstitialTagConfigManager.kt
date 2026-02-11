package com.shadowarm.admodule.ads.interstitial

class InterstitialTagConfigManager {

    private val configMap = mutableMapOf<String, InterstitialAdConfig>()

    fun register(config: InterstitialAdConfig) {
        configMap[config.tag] = config
    }

    fun registerAll(configs: List<InterstitialAdConfig>) {
        configs.forEach { register(it) }
    }

    fun get(tag: String): InterstitialAdConfig? {
        return configMap[tag]
    }

    fun clear(tag: String) {
        configMap.remove(tag)
    }

    fun clearAll() {
        configMap.clear()
    }
}
