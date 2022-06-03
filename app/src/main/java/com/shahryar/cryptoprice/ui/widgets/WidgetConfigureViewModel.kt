package com.shahryar.cryptoprice.ui.widgets

import androidx.lifecycle.*
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WidgetConfigureViewModel(private val mCurrencyRepository: CurrencyRepository): ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> = _currencies

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            mCurrencyRepository.getCurrencies(false).collect {
                if (it.status == Resource.Status.SUCCESS && !it.data.isNullOrEmpty()) {
                    _currencies.value = it.data
                }
            }
        }
    }
}