package com.shahryar.cryptoprice.data.source.local

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency

interface LocalDataSource {

    suspend fun getCurrencies(): List<Currency>

    suspend fun insertAll(currencies: List<Currency>)
}