package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.databinding.ObservableField
import androidx.lifecycle.*
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
    private val _apiKey = dataStore.readOutFromDataStore.asLiveData()
    val apiKey = mutableStateOf("")
    var apiKeyObserver: Observer<String> = Observer{
        apiKey.value = it
    }

    init {
        _apiKey.observeForever(apiKeyObserver)
    }

    fun saveApiKey() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveToDataStore(UserPreferencesRepository.PreferencesKeys.API_KEY, apiKey.value)
        }
    }

    fun onApiKeyChanged(text: String) {
        apiKey.value = text
    }

    override fun onCleared() {
        _apiKey.removeObserver(apiKeyObserver)
        super.onCleared()
    }
}