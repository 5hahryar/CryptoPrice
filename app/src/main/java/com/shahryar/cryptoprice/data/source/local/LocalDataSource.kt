package com.shahryar.cryptoprice.data.source.local

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getCurrencies(): Flow<List<Currency>>

    suspend fun insertAll(currencies: List<Currency>)
}