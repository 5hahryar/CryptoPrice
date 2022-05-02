package com.shahryar.cryptoprice.ui.settings

import androidx.lifecycle.*
import com.shahryar.shared.data.CryptoPriceSettings

class SettingsViewModel(private val appSettings: CryptoPriceSettings) :
    ViewModel() {

    private val _apiKey = MutableLiveData("")
    val apiKey: LiveData<String> = _apiKey

    init {
        _apiKey.postValue(appSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN))
    }

    fun onApiKeyChange(value: String) {
        _apiKey.value = value
    }

    fun saveApiKey() {
        appSettings.saveSetting(CryptoPriceSettings.KEYS.TOKEN, _apiKey.value.toString())
    }
}