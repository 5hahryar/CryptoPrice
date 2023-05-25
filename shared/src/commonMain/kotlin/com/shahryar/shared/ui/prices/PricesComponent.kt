package com.shahryar.shared.ui.prices

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class PricesComponent(componentContext: ComponentContext): ComponentContext by componentContext {

    val uiState: Value<UiState> = MutableValue(UiState.Loading)

    sealed interface UiState {
        object Loading: UiState
    }
//    data class UiState(
//        val isRefreshing: Boolean,
//        val isApiKeyAvailable: Boolean = false,
//        val prices: List<Currency>? = null,
//        val error: String? = null
//    )
}