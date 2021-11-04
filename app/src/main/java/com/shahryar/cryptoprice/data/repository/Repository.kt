package com.shahryar.cryptoprice.data.repository

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource

interface Repository {

    fun getCurrencies(): LiveData<List<Currency>>

    suspend fun insertCurrencies(currencies: List<Currency>)

    suspend fun refresh(): Resource<Data>
}