package com.shadowarm.admodule.ads.remote

class FakeRemoteConfigSource(
    private val values: Map<String, String>
) : RemoteConfigSource {

    override fun getString(key: String): String {
        return values[key].orEmpty()
    }

    override fun fetchAndActivate(onComplete: (success: Boolean) -> Unit) {
        onComplete(true)
    }
}
