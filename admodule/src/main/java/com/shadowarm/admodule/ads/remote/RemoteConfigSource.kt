package com.shadowarm.admodule.ads.remote

interface RemoteConfigSource {
    suspend fun fetchAndActivate(): Boolean
    fun getString(key: String): String
    fun getAll(): Map<String, String>
}

