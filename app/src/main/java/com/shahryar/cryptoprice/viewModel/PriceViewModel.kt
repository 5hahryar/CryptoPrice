package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase

class PriceViewModel(context: Context) : ViewModel() {

    private val repo = Repository(getDatabase(context))
    private val currenciesByMarket: LiveData<List<Currency>> = repo.currencies
    private val currenciesByName: LiveData<List<Currency>> = repo.currenciesByName
    private val currenciesByPrice: LiveData<List<Currency>> = repo.currenciesByPrice
    private lateinit var lastSource: LiveData<*>
    var isApiKeyAvailable: ObservableField<Boolean> = ObservableField(Utils().readStringPreference(context, KEY_PREFS_API_KEY)?.isEmpty())
    var isRefreshing: ObservableField<Boolean> = ObservableField(false)

    val currencies: MediatorLiveData<List<Currency>> = MediatorLiveData()

    init {
        currencies.addSource(currenciesByMarket) { value ->
            currencies.value = value
            lastSource = currenciesByMarket
        }
        refreshData(context)

        repo.setOnRefreshChangeListener(object : Repository.OnRefreshChangeListener {
            override fun onRefreshChanged(isRefreshing: Boolean) {
                this@PriceViewModel.isRefreshing.set(isRefreshing)
            }
        })
    }

    fun refreshData(context: Context) {
        repo.refreshData(context)

        currencies.value?.isEmpty()
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

    fun onFragmentResume(context: Context) {
        isApiKeyAvailable.set(Utils().readStringPreference(context, KEY_PREFS_API_KEY)?.isEmpty())
    }

    fun isDataEmpty(): Boolean {
        return if (currencies.value == null) true
        else currencies.value!!.isEmpty()
    }
}