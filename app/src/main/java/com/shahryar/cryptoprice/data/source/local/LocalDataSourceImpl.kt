package com.shahryar.cryptoprice.data.source.local

import com.shahryar.cryptoprice.data.model.Currency

class LocalDataSourceImpl(private val currencyDao: CurrencyDao): LocalDataSource {

    override fun getCurrencies() =
        currencyDao.getCurrencies()

    override suspend fun insertAll(currencies: List<Currency>) {
        currencyDao.insertAll(currencies)
    }
}