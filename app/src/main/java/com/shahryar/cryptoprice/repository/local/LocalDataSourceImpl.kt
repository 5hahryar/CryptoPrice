package com.shahryar.cryptoprice.repository.local

import com.shahryar.cryptoprice.model.Currency

class LocalDataSourceImpl(private val currencyDao: CurrencyDao): LocalDataSource {

    override fun getCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(currencies: List<Currency>) {
        currencyDao.insertAll(currencies)
    }
}