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

    override suspend fun getCurrencies(): List<CurrencyDto> = currencyDao.getCurrencies()

    override suspend fun insertCurrencies(currencies: List<CurrencyDto>) {
        try {
            currencyDao.addCurrencies(currencies)
        } catch (e: Exception) { }
    }
}