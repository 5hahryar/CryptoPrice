package com.shahryar.cryptoprice.repository.base

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.model.Currency

interface LocalDataSource {

    fun getCurrencies(): LiveData<List<Currency>>

    suspend fun insertAll(currencies: List<Currency>)
}