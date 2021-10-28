package com.shahryar.cryptoprice.viewModel

import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.data.repository.Repository

class WidgetConfigureViewModel(mRepository: Repository): ViewModel() {

    val currencies = mRepository.getCurrencies()
}