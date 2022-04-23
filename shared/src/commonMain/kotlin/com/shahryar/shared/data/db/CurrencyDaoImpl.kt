package com.shahryar.shared.data.db

import com.shahryar.shared.CryptoPriceDb
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.source.local.CurrencyDao
import com.squareup.sqldelight.db.SqlDriver

class CurrencyDaoImpl(
    sqlDriver: SqlDriver
): CurrencyDao {

    private val database: CryptoPriceDb = CryptoPriceDb(sqlDriver)

    override fun getCurrencies(): List<Currency> {
        TODO()
//        return database.currencyQueries.getCurrencies().executeAsList()
    }

    override fun addCurrencies(currencies: List<Currency>) {
        for (currency in currencies) {
//            database.currencyQueries.addCurrency(currency)
        }
    }
}