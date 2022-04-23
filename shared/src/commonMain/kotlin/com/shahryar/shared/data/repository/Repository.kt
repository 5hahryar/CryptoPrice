package com.shahryar.shared.data.repository


import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.Data
import com.shahryar.shared.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCurrencies(): Flow<Resource<List<Currency>>>

//    suspend fun insertCurrencies(currencies: List<Currency>)

//    @Throws(Exception::class)
//    suspend fun refresh(): Resource<List<Currency>>
}