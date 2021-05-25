package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.UserPreferencesRepository
import com.shahryar.cryptoprice.repository.local.getDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PriceViewModel(context: Context) : ViewModel() {

    private val repo = Repository(getDatabase(context))
    private val currenciesByMarket: LiveData<List<Currency>> = repo.currencies
    private val currenciesByName: LiveData<List<Currency>> = repo.currenciesByName
    private val currenciesByPrice: LiveData<List<Currency>> = repo.currenciesByPrice
    private val _isRefreshing = MutableStateFlow(false)
    private val _isApiKeyAvailable = MutableStateFlow(false)
    private lateinit var lastSource: LiveData<*>
    var latestList: List<Currency> = listOf()

    val currencies: MediatorLiveData<List<Currency>> = MediatorLiveData()
    var observer: Observer<List<Currency>>
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()
    val isApiKeyAvailable: StateFlow<Boolean>
        get() = _isApiKeyAvailable.asStateFlow()

    init {
        UserPreferencesRepository.getInstance(context).readOutFromDataStore.asLiveData().observeForever {
            viewModelScope.launch {
                _isApiKeyAvailable.emit(it.isNotEmpty())
            }
        }

        currencies.addSource(currenciesByMarket) { value ->
            currencies.value = value
            lastSource = currenciesByMarket
        }

        refreshData(context)

        observer = Observer {
            viewModelScope.launch {
                _isRefreshing.emit(false)
            }
        }
        currencies.observeForever(observer)
    }

    fun refreshData(context: Context) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            repo.refreshData(context)
            currencies.value?.isEmpty()
        }
    }

    fun onSort(sortKey: String) {
        this.currencies.removeSource(lastSource)
        when (sortKey) {
            "name" -> currencies.addSource(currenciesByName) { value ->
                currencies.value = value
                lastSource = currenciesByName
            }
            "price" -> currencies.addSource(currenciesByPrice) { value ->
                currencies.value = value
                lastSource = currenciesByPrice
            }
            else -> currencies.addSource(currenciesByMarket) { value ->
                currencies.value = value
                lastSource = currenciesByMarket
            }
        }
    }


}