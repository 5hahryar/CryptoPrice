package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase

class PriceViewModel(context: Context) : ViewModel() {

    private val repo = Repository(getDatabase(context))
    private val currenciesByMarket: LiveData<List<Currency>> = repo.currencies
    private val currenciesByName: LiveData<List<Currency>> = repo.currenciesByName
    private val currenciesByPrice: LiveData<List<Currency>> = repo.currenciesByPrice
    private lateinit var lastSource: LiveData<*>

    val currencies: MediatorLiveData<List<Currency>> = MediatorLiveData()

    init {
        currencies.addSource(currenciesByMarket) { value ->
            currencies.value = value
            lastSource = currenciesByMarket
        }
        refreshData(context)
    }

    fun refreshData(context: Context) {
        repo.refreshData(context)
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