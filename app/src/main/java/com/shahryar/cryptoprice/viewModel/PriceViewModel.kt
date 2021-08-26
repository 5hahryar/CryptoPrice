package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.base.Repository
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.launch

class PriceViewModel(context: Context, private val mRepository: Repository) : ViewModel() {

    val currencies: LiveData<List<Currency>> = mRepository.getCurrencies()

//    @JvmName("getCurrencies1")
//    fun getCurrencies() = liveData {
////        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = mRepository.getCurrencies()))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }

    //    private val repo = RepositoryImpl(getDatabase(context))
//    private val currenciesByMarket: LiveData<List<Currency>> = repo.currencies
//    private val currenciesByName: LiveData<List<Currency>> = repo.currenciesByName
//    private val currenciesByPrice: LiveData<List<Currency>> = repo.currenciesByPrice
    private lateinit var lastSource: LiveData<*>
    var isApiKeyAvailable: ObservableField<Boolean> = ObservableField()
    var isDataEmpty: ObservableField<Boolean> = ObservableField()
    var isRefreshing: ObservableField<Boolean> = ObservableField(false)
    var latestList: List<Currency> = listOf()

//    val currencies: MediatorLiveData<List<Currency>> = MediatorLiveData()

    init {
        UserPreferencesRepository.getInstance(context).readOutFromDataStore.asLiveData()
            .observeForever() {
                if (it.isEmpty()) isApiKeyAvailable.set(false)
                else isApiKeyAvailable.set(true)
            }
//
//        currencies.addSource(currenciesByMarket) { value ->
//            currencies.value = value
//            lastSource = currenciesByMarket
//        }
//
//        refreshData(context)
//
//        repo.setOnRefreshChangeListener(object : RepositoryImpl.OnRefreshChangeListener {
//            override fun onRefreshChanged(isRefreshing: Boolean) {
//                this@PriceViewModel.isRefreshing.set(isRefreshing)
//            }
//        })
//
//        currencies.observeForever {
//            isDataEmpty.set(it.isEmpty())
//        }
    }

    fun refreshData() {
        viewModelScope.launch { mRepository.refresh() }
    }


    fun sort(sortKey: String) {
//        this.currencies.removeSource(lastSource)
//        when (sortKey) {
//            "name" -> currencies.addSource(currenciesByName) { value ->
//                currencies.value = value
//                lastSource = currenciesByName
//            }
//            "price" -> currencies.addSource(currenciesByPrice) { value ->
//                currencies.value = value
//                lastSource = currenciesByPrice
//            }
//            else -> currencies.addSource(currenciesByMarket) { value ->
//                currencies.value = value
//                lastSource = currenciesByMarket
//            }
//        }
//    }
    }
}