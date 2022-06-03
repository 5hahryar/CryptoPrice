package com.shahryar.shared.data.source.local

import com.shahryar.shared.CryptoPriceDb
import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.CurrencyEntityMapper
import com.squareup.sqldelight.db.SqlDriver

class CurrencyDaoImpl(
    sqlDriver: SqlDriver
): CurrencyDao {

    private val database: CryptoPriceDb = CryptoPriceDb(sqlDriver)

    override suspend fun getCurrencies(): List<CurrencyDto> {
        return database.currencyEntityQueries.getCurrencies().executeAsList().map { CurrencyEntityMapper().mapTo(it) }
    }

    override suspend fun addCurrencies(currencies: List<CurrencyDto>) {
        for (currency in currencies) {
            database.currencyEntityQueries.addCurrency(CurrencyEntityMapper().mapFrom(currency))
        }
    }
}