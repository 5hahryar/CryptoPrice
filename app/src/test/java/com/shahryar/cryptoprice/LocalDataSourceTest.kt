package com.shahryar.cryptoprice

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.base.LocalDataSource

class LocalDataSourceTest: LocalDataSource {
    override fun getCurrencies(): LiveData<List<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }
}