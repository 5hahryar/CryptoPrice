package com.shahryar.shared.data.source.local


import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getCurrencies(): Resource<List<CurrencyDto>>

    suspend fun insertCurrencies(currencies: List<CurrencyDto>)
}