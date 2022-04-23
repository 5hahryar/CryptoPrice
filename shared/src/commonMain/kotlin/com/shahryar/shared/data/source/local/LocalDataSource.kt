package com.shahryar.shared.data.source.local


import com.shahryar.shared.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getCurrencies(): Flow<List<Currency>>
//
//    suspend fun insertAll(currencies: List<Currency>)
}