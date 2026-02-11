package com.shadowarm.admodule.ads.remote

interface RemoteConfigProvider {
    suspend fun fetchAndActivate(): Boolean
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String
    fun getLong(key: String): Long
}
