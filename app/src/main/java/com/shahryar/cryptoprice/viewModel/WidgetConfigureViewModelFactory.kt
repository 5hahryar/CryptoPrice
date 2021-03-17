package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WidgetConfigureViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WidgetConfigureViewModel::class.java)) {
            return WidgetConfigureViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
