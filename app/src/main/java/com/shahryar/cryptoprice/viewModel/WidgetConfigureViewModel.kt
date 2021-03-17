package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase

class WidgetConfigureViewModel(context: Context): ViewModel() {
    val currencies = Repository(getDatabase(context)).currencies
}