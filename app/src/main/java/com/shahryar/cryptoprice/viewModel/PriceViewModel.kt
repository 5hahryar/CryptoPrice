package com.shahryar.cryptoprice.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.repository.base.Repository
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.launch

class PriceViewModel(preferences: UserPreferencesRepository, private val mRepository: Repository) : ViewModel() {

    val currencies: LiveData<List<Currency>> = mRepository.getCurrencies()
    var isApiKeyAvailable: ObservableField<Boolean> = ObservableField()
    var isDataEmpty: ObservableField<Boolean> = ObservableField()
    var isRefreshing: ObservableField<Boolean> = ObservableField(false)

    private val preferencesObserver = Observer<String> {
        if (it.isEmpty()) isApiKeyAvailable.set(false)
        else {
            isApiKeyAvailable.set(true)
            refreshData()
        }
    }

    init {
        preferences.readOutFromDataStore.asLiveData().observeForever(preferencesObserver)
    }

    fun refreshData() {
        viewModelScope.launch { mRepository.refresh() }
    }
}