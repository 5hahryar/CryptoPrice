package com.shahryar.cryptoprice.settings

import androidx.lifecycle.*
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    private val _apiKey = MutableLiveData("")
    val apiKey: LiveData<String> = _apiKey

    init {
        viewModelScope.launch {
            preferencesRepository.readOutFromDataStore.collect {
                _apiKey.value = it.apiKey
            }
        }
    }

    fun onApiKeyChange(value: String) {
        _apiKey.value = value
    }

    fun saveApiKey() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiKey.value?.let {
                preferencesRepository.saveToDataStore(
                    UserPreferencesRepository.PreferencesKeys.API_KEY,
                    it
                )
            }
        }
    }
}