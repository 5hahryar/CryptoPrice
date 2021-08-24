package com.shahryar.cryptoprice.repository.local

import com.shahryar.cryptoprice.model.Currency

interface LocalDataSource {

    suspend fun getCurrencies(): List<Currency>

    suspend fun insertAll(currencies: List<Currency>)
}