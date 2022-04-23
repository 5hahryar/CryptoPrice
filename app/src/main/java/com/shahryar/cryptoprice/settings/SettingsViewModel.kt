package com.shahryar.cryptoprice.settings

import androidx.lifecycle.*
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import com.shahryar.shared.data.CryptoPriceSettings

class SettingsViewModel(private val preferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    private val _apiKey = MutableLiveData("")
    val apiKey: LiveData<String> = _apiKey

    init {
        _apiKey.postValue(CryptoPriceSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN))
//        viewModelScope.launch {
//            preferencesRepository.readOutFromDataStore.collect {
//                _apiKey.value = it.apiKey
//            }
//        }
    }

    fun onApiKeyChange(value: String) {
        _apiKey.value = value
    }

    fun saveApiKey() {
        CryptoPriceSettings.saveSetting(CryptoPriceSettings.KEYS.TOKEN, _apiKey.value.toString())
//        viewModelScope.launch(Dispatchers.IO) {
//            _apiKey.value?.let {
//                preferencesRepository.saveToDataStore(
//                    UserPreferencesRepository.PreferencesKeys.API_KEY,
//                    it
//                )
//            }
//        }
    }
}