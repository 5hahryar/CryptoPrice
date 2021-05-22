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
    private lateinit var lastSource: LiveData<*>
    var isApiKeyAvailable: ObservableField<Boolean> = ObservableField()
    var isDataEmpty: ObservableField<Boolean> = ObservableField()
//    var isRefreshing: ObservableField<Boolean> = ObservableField(false)
    var latestList: List<Currency> = listOf()

    val currencies: MediatorLiveData<List<Currency>> = MediatorLiveData()
    lateinit var observer: Observer<List<Currency>>
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        UserPreferencesRepository.getInstance(context).readOutFromDataStore.asLiveData().observeForever() {
            if (it.isEmpty()) isApiKeyAvailable.set(false)
            else isApiKeyAvailable.set(true)
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

//        repo.setOnRefreshChangeListener(object : Repository.OnRefreshChangeListener {
//            override fun onRefreshChanged(isRefreshing: Boolean) {
//                this@PriceViewModel._isRefreshing.emit(true)
//            }
//        })

        currencies.observeForever {
            isDataEmpty.set(it.isEmpty())
        }
    }

    fun refreshData(context: Context) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            repo.refreshData(context)
            currencies.value?.isEmpty()
        }
    }

    fun sort(sortKey: String) {
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