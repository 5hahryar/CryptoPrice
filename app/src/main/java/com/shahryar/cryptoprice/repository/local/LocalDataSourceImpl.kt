package com.shahryar.cryptoprice.repository.local

import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.repository.base.LocalDataSource

class LocalDataSourceImpl(private val currencyDao: CurrencyDao): LocalDataSource {

    override fun getCurrencies() =
        currencyDao.getCurrencies()

    override suspend fun insertAll(currencies: List<Currency>) {
        currencyDao.insertAll(currencies)
    }
}