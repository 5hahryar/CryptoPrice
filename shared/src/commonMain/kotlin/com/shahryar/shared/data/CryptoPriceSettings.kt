package com.shahryar.shared.data

import com.russhwolf.settings.*
import com.russhwolf.settings.coroutines.getStringFlow
import com.shahryar.shared.data.CryptoPriceSettings.KEYS.TOKEN
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CryptoPriceSettings: KoinComponent {

    private val settings: ObservableSettings by inject()

    object KEYS {
        const val TOKEN = "token"
    }

    fun observeToken(onChange: (String?) -> Unit) {
        settings.addStringOrNullListener(TOKEN, onChange)
    }

    fun deleteSetting(key: String) {
        settings.remove(key)
    }

    fun saveSetting(key: String, value: String) {
        settings[key] = value
    }

    fun getSetting(key: String): String? {
        return settings[key]
    }
}