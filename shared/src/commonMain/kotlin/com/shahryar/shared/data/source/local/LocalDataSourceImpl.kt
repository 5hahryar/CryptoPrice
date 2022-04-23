package com.shahryar.shared.data.source.local

import com.shahryar.shared.data.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSourceImpl(
    private val currencyDao: CurrencyDao
): LocalDataSource {

    override fun getCurrencies(): Flow<List<Currency>> {
        return flow { emit(currencyDao.getCurrencies()) }.flowOn(Dispatchers.Default)
    }

}