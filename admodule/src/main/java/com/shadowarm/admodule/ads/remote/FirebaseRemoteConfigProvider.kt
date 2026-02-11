package com.shadowarm.admodule.ads.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await

class FirebaseRemoteConfigProvider(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigProvider {

    override suspend fun fetchAndActivate(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    override fun getBoolean(key: String): Boolean =
        remoteConfig.getBoolean(key)

    override fun getString(key: String): String =
        remoteConfig.getString(key)

    override fun getLong(key: String): Long =
        remoteConfig.getLong(key)
}
