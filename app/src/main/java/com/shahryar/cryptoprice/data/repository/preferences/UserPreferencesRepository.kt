package com.shahryar.cryptoprice.data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shahryar.cryptoprice.CryptoPriceApplication
import com.shahryar.cryptoprice.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val userPreferencesStore: DataStore<Preferences>) {

    val readOutFromDataStore: Flow<UserPreferences> = userPreferencesStore.data
        .catch {
            if (this is IOException) {
                emit(emptyPreferences())
            }
        }.map { preference ->
            val apiKey: String = preference[PreferencesKeys.API_KEY] ?: ""
            CryptoPriceApplication.apiKey = apiKey
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
