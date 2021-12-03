package com.shahryar.cryptoprice.widgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.data.repository.Repository

class WidgetConfigureViewModel(mRepository: Repository): ViewModel() {

    val currencies = liveData { emit(mRepository.getCurrencies()) }
}