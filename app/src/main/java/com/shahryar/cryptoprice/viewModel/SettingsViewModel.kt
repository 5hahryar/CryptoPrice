package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(context: Context) : ViewModel() {

    private val dataStore = UserPreferencesRepository.getInstance(context)
    val apiKey = dataStore.readOutFromDataStore.asLiveData()

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveToDataStore(UserPreferencesRepository.PreferencesKeys.API_KEY, apiKey)
        }
    }
}