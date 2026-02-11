package com.shadowarm.admodule.ads.remote

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class RemoteConfigManager(
    private val source: RemoteConfigSource
) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun fetch() {
        source.fetchAndActivate()
    }

    fun <T> getConfig(
        key: String,
        serializer: KSerializer<T>
    ): T? {
        val raw = source.getString(key)
        if (raw.isBlank()) return null

        return runCatching {
            json.decodeFromString(serializer, raw)
        }.getOrNull()
    }

    fun getRaw(key: String): String? =
        source.getString(key).takeIf { it.isNotBlank() }

    fun getAll(): Map<String, String> =
        source.getAll()
}

