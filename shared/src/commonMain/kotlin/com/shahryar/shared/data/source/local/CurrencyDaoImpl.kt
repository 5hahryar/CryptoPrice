package com.shahryar.shared.data.source.local

import com.shahryar.shared.CryptoPriceDb
import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.mapToCurrency
import com.shahryar.shared.data.model.mapToCurrencyDto
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyDaoImpl(
    sqlDriver: SqlDriver
): CurrencyDao {

    private val database: CryptoPriceDb = CryptoPriceDb(sqlDriver)

    override suspend fun getCurrencies(): List<CurrencyDto> {
        return database.currencyEntityQueries.getCurrencies().executeAsList().map { it.mapToCurrencyDto() }
    }

    override suspend fun addCurrencies(currencies: List<CurrencyDto>) {
        for (currency in currencies) {
            database.currencyEntityQueries.addCurrency(currency.mapToCurrency())
        }
    }
}