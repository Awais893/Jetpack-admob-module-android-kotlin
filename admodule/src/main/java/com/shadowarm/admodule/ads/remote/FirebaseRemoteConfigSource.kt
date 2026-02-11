package com.shadowarm.admodule.ads.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.tasks.await

class FirebaseRemoteConfigSource(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigSource {

    override suspend fun fetchAndActivate(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    override fun getString(key: String): String {
        return remoteConfig.getString(key)
    }

    override fun getAll(): Map<String, String> {
        return remoteConfig.all
            .mapValues { it.value.asString() }
            .filterValues { it.isNotBlank() }
    }
}

