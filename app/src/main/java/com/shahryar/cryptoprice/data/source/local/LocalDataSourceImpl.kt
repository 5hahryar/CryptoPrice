package com.shahryar.cryptoprice.data.source.local

import com.shahryar.cryptoprice.data.model.Currency
import kotlinx.coroutines.flow.flow

class LocalDataSourceImpl(private val currencyDao: CurrencyDao): LocalDataSource {

    override fun getCurrencies() =
//        currencyDao.getCurrencies()
        flow<List<Currency>> { listOf<Currency>() }

    override suspend fun insertAll(currencies: List<Currency>) {
//        currencyDao.insertAll(currencies)
    }
}