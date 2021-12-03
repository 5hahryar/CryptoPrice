package com.shahryar.cryptoprice.data.repository.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shahryar.cryptoprice.CryptoPriceApplication
import com.shahryar.cryptoprice.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

class UserPreferencesRepository(private val userPreferencesStore: DataStore<Preferences>) {

    var apiKey: String = runBlocking { userPreferencesStore.data.first()[PreferencesKeys.API_KEY].toString() }

    val readOutFromDataStore: Flow<UserPreferences> = userPreferencesStore.data
        .catch {
            if (this is IOException) {
                emit(emptyPreferences())
            }
        }.map { preference ->
            apiKey = preference[PreferencesKeys.API_KEY] ?: ""
            Log.d("API", "read from datastore $apiKey")
            UserPreferences(apiKey)
        }

    object PreferencesKeys {
        val API_KEY = stringPreferencesKey("api_key")
    }

    suspend fun saveToDataStore(key: Preferences.Key<String>, value: String) {
        // TODO: This is a memory leak
        userPreferencesStore.edit { preference ->
            preference[key] = value
        }
    }
}
