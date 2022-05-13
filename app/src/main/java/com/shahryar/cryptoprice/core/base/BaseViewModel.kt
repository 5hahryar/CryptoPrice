package com.shahryar.cryptoprice.core.base

import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    var toastMessage = SingleLiveEvent<String>()
}