package com.shahryar.cryptoprice.prices.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.model.UserPreferences
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PriceViewModel(preferences: UserPreferencesRepository, private val mRepository: Repository) : ViewModel() {

    val currencies: LiveData<List<Currency>> = mRepository.getCurrencies()
    var isApiKeyAvailable: ObservableField<Boolean> = ObservableField()

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val preferencesObserver = Observer<UserPreferences> {
        if (it.apiKey.isEmpty()) isApiKeyAvailable.set(false)
        else {
            isApiKeyAvailable.set(true)
            refreshData()
        }
    }

    private val currenciesObserver = Observer<List<Currency>> {
        _isRefreshing.value = false
    }

    init {
        preferences.readOutFromDataStore.asLiveData().observeForever(preferencesObserver)
        currencies.observeForever(currenciesObserver)
    }

    fun refreshData() {
        _isRefreshing.value = true
        viewModelScope.launch {
            val result = mRepository.refresh()
            if (result.status == Resource.Status.ERROR) {
                _uiState.value = _uiState.value?.copy(isRefreshing = false, errorMessage = result.message)
            }
//            _isRefreshing.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        currencies.removeObserver(currenciesObserver)
    }

    data class UiState(
        val isRefreshing: Boolean,
        val currencies: List<Currency>? = null,
        val errorMessage: String? = null
    )
}