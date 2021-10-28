package com.shahryar.cryptoprice.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.shahryar.cryptoprice.data.model.Currency
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
    var isDataEmpty: ObservableField<Boolean> = ObservableField()

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
        viewModelScope.launch { mRepository.refresh() }
    }

    override fun onCleared() {
        super.onCleared()
        currencies.removeObserver(currenciesObserver)
    }
}