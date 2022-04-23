package com.shahryar.shared.data.source.local

import com.shahryar.shared.data.model.Currency


interface CurrencyDao {

    fun getCurrencies(): List<Currency>

    fun addCurrencies(currencies: List<Currency>)
}