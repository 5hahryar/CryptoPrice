package com.shahryar.cryptoprice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferencesRepository: UserPreferencesRepository) : ViewModel() {

    val apiKey = preferencesRepository.readOutFromDataStore.asLiveData()

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.saveToDataStore(UserPreferencesRepository.PreferencesKeys.API_KEY, apiKey)
        }
    }
}