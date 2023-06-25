package com.shahryar.shared.ui.prices

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PricesScreenModel : ScreenModel, KoinComponent {

    private val currencyRepository: CurrencyRepository by inject()
    val uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    init {
        CryptoPriceSettings.observeToken { token ->
            if (token.isEmpty()) {
                uiState.update { UiState.ApiKeyNotAvailable }
            }
        }
        getCurrencies(true)
    }

    private fun getCurrencies(fetchFromRemote: Boolean = false) {
        uiState.update { UiState.Loading }
        coroutineScope.launch {
            currencyRepository.getCurrencies(fetchFromRemote)
                .collect { resource ->
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        uiState.update { UiState.Success(prices = resource.data) }
                    } else uiState.update { UiState.Error(message = resource.message.toString()) }
                }
        }
    }

    sealed interface UiState {
        object Loading : UiState
        class Success(val prices: List<Currency>) : UiState
        object ApiKeyNotAvailable : UiState
        class Error(val message: String) : UiState
    }
}