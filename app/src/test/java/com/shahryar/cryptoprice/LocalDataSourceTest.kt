package com.shahryar.cryptoprice

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.source.local.LocalDataSource

class LocalDataSourceTest: LocalDataSource {
    override suspend fun getCurrencies(): LiveData<List<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }
}