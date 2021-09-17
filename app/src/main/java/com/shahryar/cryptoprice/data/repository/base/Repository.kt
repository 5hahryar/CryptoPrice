package com.shahryar.cryptoprice.data.repository.base

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency

interface Repository {

    fun getCurrencies(): LiveData<List<Currency>>

    suspend fun insertCurrencies(currencies: List<Currency>)

    suspend fun refresh()
}