package com.shahryar.cryptoprice.ui.prices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.core.base.BaseViewModel
import com.shahryar.shared.data.model.CurrencyDto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PriceViewModel(
    private val mCurrencyRepository: com.shahryar.shared.data.repository.CurrencyRepository
) :
    BaseViewModel() {

    private val _uiState = MutableLiveData(UiState(true))
    val uiState: LiveData<UiState> = _uiState

    private val _selectedCurrency = MutableLiveData<CurrencyDto>()
    val selectedCurrency: LiveData<CurrencyDto> = _selectedCurrency

    init {
        getCurrencies(true)
    }

    private fun getCurrencies(fetchFromRemote: Boolean = false) {
        _uiState.value = _uiState.value?.copy(isRefreshing = true)
        viewModelScope.launch {
            mCurrencyRepository.getCurrencies(fetchFromRemote).collect {
                if (it.status == com.shahryar.shared.data.model.Resource.Status.SUCCESS) {
                    _uiState.value = _uiState.value?.copy(isRefreshing = false, prices = it.data)
                } else _uiState.value =
                    _uiState.value?.copy(isRefreshing = false, error = it.message)
            }
        }
    }

    fun selectCurrency(currency: CurrencyDto) {
        _selectedCurrency.value = currency
    }

    fun refreshPrices() {
        getCurrencies(true)
    }

    data class UiState(
        val isRefreshing: Boolean,
        val prices: List<CurrencyDto>? = null,
        val error: String? = null
    )
}