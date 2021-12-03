package com.shahryar.cryptoprice.core.common

import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.core.mvvm.SingleLiveEvent

open class BaseViewModel: ViewModel() {

    var toastMessage = SingleLiveEvent<String>()
}