package com.shahryar.shared.data.source.local

import com.shahryar.shared.data.model.CurrencyDto


interface CurrencyDao {

    suspend fun getCurrencies(): List<CurrencyDto>

    suspend fun addCurrencies(currencies: List<CurrencyDto>)
}