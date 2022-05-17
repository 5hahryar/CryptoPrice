package com.shahryar.shared.data.source.local

import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LocalDataSourceImpl(
    private val currencyDao: CurrencyDao
) : LocalDataSource {

    override suspend fun getCurrencies(): Resource<List<CurrencyDto>> {
        return try {
            val currencies = currencyDao.getCurrencies()
            if (!currencies.isNullOrEmpty()) {
                Resource.success(currencies)
            } else Resource.error(message = "No currency found")
        } catch (e: Exception) {
            Resource.error(message = e.message ?: "Error reading from database")
        }
    }

    override suspend fun insertCurrencies(currencies: List<CurrencyDto>) {
        try {
            currencyDao.addCurrencies(currencies)
        } catch (e: Exception) { }
    }
}