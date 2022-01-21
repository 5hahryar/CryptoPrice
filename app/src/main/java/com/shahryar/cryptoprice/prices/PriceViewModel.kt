package com.shahryar.cryptoprice.prices

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

class PriceViewModel(
    private val preferences: UserPreferencesRepository,
    private val mRepository: Repository
) :
    BaseViewModel() {

    private val _uiState = MutableLiveData(UiState(true))
    val uiState: LiveData<UiState> = _uiState

    private var apiKey: String = ""

    init {
        observeApiKey()
        refreshData()
        getCurrencies()
    }

    private fun observeApiKey() {
        viewModelScope.launch {
            preferences.readOutFromDataStore.collect {
                apiKey = it.apiKey
                if (it.apiKey.isEmpty()) _uiState.value =
                    UiState(isRefreshing = false, isApiKeyAvailable = false)
                else refreshData()
            }
        }
    }

    private fun getCurrencies() {
        if (apiKey.isNotEmpty()) {
            viewModelScope.launch {
                mRepository.getCurrencies().collect { currencies ->
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
        if (apiKey.isNotEmpty()) {
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
    }

    data class UiState(
        val isRefreshing: Boolean,
        val isApiKeyAvailable: Boolean = true,
        val currencies: List<Currency>? = null,
        val errorMessage: String? = null
    )
}