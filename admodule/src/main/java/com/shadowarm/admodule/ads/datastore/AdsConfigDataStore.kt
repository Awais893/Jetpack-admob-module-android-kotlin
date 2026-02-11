package com.shadowarm.admodule.ads.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class AdsConfigDataStore(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun save(tag: String, json: String) {
        dataStore.edit {
            it[stringPreferencesKey(tag)] = json
        }
    }

    suspend fun getAll(): Map<String, String> {
        return dataStore.data.first()
            .asMap()
            .mapNotNull { entry ->
                val key = entry.key.name
                val value = entry.value as? String
                if (value != null) key to value else null
            }.toMap()
    }
}
