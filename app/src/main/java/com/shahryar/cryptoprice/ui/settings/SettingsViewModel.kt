package com.shahryar.cryptoprice.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.shahryar.shared.data.CryptoPriceSettings

class SettingsViewModel(private val appSettings: CryptoPriceSettings) :
    ViewModel() {

    var apiKey by mutableStateOf(appSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN))
        private set

    fun onApiKeyChange(value: String) {
        apiKey = value
    }

    fun saveApiKey() {
        apiKey?.let { appSettings.saveSetting(CryptoPriceSettings.KEYS.TOKEN, it) }
    }
}