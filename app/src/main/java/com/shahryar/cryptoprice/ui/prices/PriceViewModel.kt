package com.shahryar.cryptoprice.ui.prices

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.core.base.BaseViewModel
import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class PriceViewModel(
    private val currencyRepository: CurrencyRepository
) :
    BaseViewModel() {

    var uiState by mutableStateOf(UiState(true,
        isApiKeyAvailable = !CryptoPriceSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN)
            .isNullOrEmpty()
    )
    )
        private set

    var selectedCurrency by mutableStateOf<CurrencyDto?>(null)
        private set

    init {
        CryptoPriceSettings.observeToken { token ->
            uiState = uiState.copy(isApiKeyAvailable = !token.isNullOrEmpty())
            Log.d("OBS", "is empty: ${token.isNullOrEmpty()}")
        }
        getCurrencies(true)
    }

    private fun getCurrencies(fetchFromRemote: Boolean = false) {
        uiState = uiState.copy(isRefreshing = true)
        viewModelScope.launch {
            currencyRepository.getCurrencies(fetchFromRemote)
                .onCompletion {
                    uiState = uiState.copy(isRefreshing = false)
                }.collect {
                    uiState = if (it.status == Resource.Status.SUCCESS) {
                        uiState.copy(prices = it.data)
                    } else uiState.copy(error = it.message, prices = it.data)
                }
        }
    }

    fun selectCurrency(currency: CurrencyDto) {
        selectedCurrency = currency
    }

    fun refreshPrices() {
        getCurrencies(true)
    }

    fun errorShown() {
        uiState = uiState.copy(error = null)
    }

    data class UiState(
        val isRefreshing: Boolean,
        val isApiKeyAvailable: Boolean = false,
        val prices: List<CurrencyDto>? = null,
        val error: String? = null
    )
}