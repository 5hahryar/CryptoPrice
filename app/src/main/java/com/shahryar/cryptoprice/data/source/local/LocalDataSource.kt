package com.shahryar.cryptoprice.data.source.local

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency

interface LocalDataSource {

    fun getCurrencies(): LiveData<List<Currency>>

    suspend fun insertAll(currencies: List<Currency>)
}