package com.shahryar.cryptoprice.prices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.core.common.BaseViewModel
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PriceViewModel(private val preferences: UserPreferencesRepository, private val mRepository: Repository) :
    BaseViewModel() {

    private val _uiState = MutableLiveData(UiState(true))
    val uiState: LiveData<UiState> = _uiState

    init {
        refreshData()
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            preferences.readOutFromDataStore.collect {
                if (it.apiKey.isEmpty()) _uiState.value =
                    UiState(isRefreshing = false, isApiKeyAvailable = false)
                else {
                    val currencies = mRepository.getCurrencies()
                    if (!currencies.isNullOrEmpty()) _uiState.value =
                        UiState(isRefreshing = false, currencies = currencies)
                    else _uiState.value = UiState(
                        isRefreshing = false,
                        errorMessage = "Error fetching from database"
                    )
                }
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isRefreshing = true)
            val result = mRepository.refresh()
            _uiState.value = _uiState.value?.copy(isRefreshing = false)
            result.message?.let {
                toastMessage.value = it
            }
            if (result.status == Resource.Status.ERROR) {
                _uiState.value =
                    _uiState.value?.copy(isRefreshing = false, errorMessage = result.message)
            }
        }
    }

    data class UiState(
        val isRefreshing: Boolean,
        val isApiKeyAvailable: Boolean = true,
        val currencies: List<Currency>? = null,
        val errorMessage: String? = null
    )
}