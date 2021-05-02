package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.repository.UserPreferencesRepository
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsViewModel(context: Context) : ViewModel() {

    private val dataStore = UserPreferencesRepository.getInstance(context)
    val apiKey = dataStore.readOutFromDataStore.asLiveData()

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveToDataStore(UserPreferencesRepository.PreferencesKeys.API_KEY, apiKey)
        }
    }
}