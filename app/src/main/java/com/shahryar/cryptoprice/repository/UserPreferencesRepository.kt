package com.shahryar.cryptoprice.repository

import android.content.Context
import androidx.datastore.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository private constructor(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    val readOutFromDataStore: Flow<String> = context.dataStore.data
        .catch {
            if (this is IOException) {
                emit(emptyPreferences())
            }
        }.map { preference ->
            val apiKey: String = preference[PreferencesKeys.API_KEY] ?: ""
            apiKey
        }

    object PreferencesKeys {
        val API_KEY = stringPreferencesKey("api_key")
    }

    suspend fun saveToDataStore(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { preference ->
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
