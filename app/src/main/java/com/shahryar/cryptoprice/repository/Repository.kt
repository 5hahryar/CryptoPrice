package com.shahryar.cryptoprice.repository

import com.shahryar.cryptoprice.model.Currency

interface Repository {

    suspend fun getCurrencies(): List<Currency>

    suspend fun insertCurrencies(currencies: List<Currency>)
}