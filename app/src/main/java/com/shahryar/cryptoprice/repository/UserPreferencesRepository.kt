package com.shahryar.cryptoprice.repository

import android.content.Context
import androidx.datastore.*
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(context: Context) {

    private val dataStore: DataStore<Preferences> =  context.createDataStore("preferences")

    val readOutFromDataStore: Flow<String> = dataStore.data
        .catch {
            if (this is IOException) {
                emit(emptyPreferences())
            }
        }.map { preference ->
            val apiKey: String = preference[PreferencesKeys.API_KEY] ?: ""
            apiKey
        }

    object PreferencesKeys {
        val API_KEY = preferencesKey<String>("api_key")
    }

    suspend fun saveToDataStore(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
