package com.shahryar.cryptoprice.prices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.core.common.BaseViewModel
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import com.shahryar.shared.data.model.DataX
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PriceViewModel(
    private val preferences: UserPreferencesRepository,
    private val mRepository: com.shahryar.shared.data.repository.Repository
) :
    BaseViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState: LiveData<UiState> = _uiState

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _selectedCurrency = MutableLiveData<Currency>()
    val selectedCurrency: LiveData<Currency> = _selectedCurrency

    private var apiKey: String = "not empty"

    val text = MutableLiveData("")

    init {
//        observeApiKey()
//        refreshData()
        getCurrencies()
    }

    private fun observeApiKey() {
        viewModelScope.launch {
            preferences.readOutFromDataStore.collect {
                apiKey = it.apiKey
                if (it.apiKey.isEmpty()) _uiState.value =
                    UiState.Error("Api key not found")
                else refreshData()
            }
        }
    }

    private fun getCurrencies() {
        if (apiKey.isNotEmpty()) {
            viewModelScope.launch {
                mRepository.getCurrencies().collect {
                    if (it.status == com.shahryar.shared.data.model.Resource.Status.SUCCESS) {
                        _uiState.postValue(UiState.Success(it.data!!))
                    } else _uiState.postValue(UiState.Error(it.message.toString()))
                }
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        _selectedCurrency.value = currency
    }

    fun refreshData() {
//        if (apiKey.isNotEmpty()) {
//            viewModelScope.launch {
//                _isRefreshing.value = true
//                val result = mRepository.refresh()
//                _isRefreshing.value = false
//                result.message?.let {
//                    toastMessage.value = it
//                }
//                if (result.status == Resource.Status.ERROR) {
//                    _isRefreshing.value = false
//                    _uiState.value =
//                        UiState.Error(result.message.toString())
//                }
//            }
//        }
    }

    sealed class UiState {
        object Loading: UiState()
        class Error(val message: String): UiState()
        class Success(val currencies: List<com.shahryar.shared.data.model.Currency>): UiState()
    }
//    data class UiState(
//        val isRefreshing: Boolean,
//        val isApiKeyAvailable: Boolean = true,
//        val currencies: List<Currency>? = null,
//        val errorMessage: String? = null
//    )
}