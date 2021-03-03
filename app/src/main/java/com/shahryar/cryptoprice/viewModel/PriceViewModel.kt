package com.shahryar.cryptoprice.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase

class PriceViewModel(context: Context): ViewModel() {

    private val repo = Repository(getDatabase(context))
    val currencies: LiveData<List<Currency>> = repo.currencies

    init {
        refreshData(context)
    }

    fun refreshData(context: Context) {
        repo.refreshData(context)
    }

    fun sort(sortKey: String, context: Context) {
        TODO()
    }
}