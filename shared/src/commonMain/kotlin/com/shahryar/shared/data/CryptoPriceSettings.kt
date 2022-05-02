package com.shahryar.shared.data

import com.russhwolf.settings.*
import com.russhwolf.settings.coroutines.getStringFlow
import com.shahryar.shared.data.CryptoPriceSettings.KEYS.TOKEN
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CryptoPriceSettings: KoinComponent {

    object KEYS {
        const val TOKEN = "token"
    }

    @OptIn(ExperimentalSettingsApi::class)
    private val settings: ObservableSettings by inject()

    @OptIn(ExperimentalSettingsApi::class)
    fun observeToken(onChange: (String?) -> Unit) {
        settings.addStringOrNullListener(TOKEN, onChange)
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun deleteSetting(key: String) {
        settings.remove(key)
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun saveSetting(key: String, value: String) {
        settings[key] = value
    }

    @OptIn(ExperimentalSettingsApi::class)
    fun getSetting(key: String): String? {
        return settings[key]
    }
}