package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils

class SettingsViewModel(context: Context) : ViewModel() {

    val apiKey: ObservableField<String?> = ObservableField()

    init {
        apiKey.set(Utils().readStringPreference(context, KEY_PREFS_API_KEY))
    }
}