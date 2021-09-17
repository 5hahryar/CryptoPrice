package com.shahryar.cryptoprice.data.repository.local

import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.repository.base.LocalDataSource

class LocalDataSourceImpl(private val currencyDao: CurrencyDao): LocalDataSource {

    override fun getCurrencies() =
        currencyDao.getCurrencies()

    override suspend fun insertAll(currencies: List<Currency>) {
        currencyDao.insertAll(currencies)
    }
}