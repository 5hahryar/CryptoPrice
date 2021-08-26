package com.shahryar.cryptoprice.repository.base

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.Data

interface Repository {

    fun getCurrencies(): LiveData<List<Currency>>

    suspend fun insertCurrencies(currencies: List<Currency>)

    suspend fun refresh()
}