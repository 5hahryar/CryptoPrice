package com.shahryar.cryptoprice

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String
import kotlin.Unit

public interface CurrencyQueries : Transacter {
  public fun <T : Any> getCurrencies(mapper: (
    id: Long,
    name: String,
    symbol: String,
    market_cap: String,
    price: Double,
    percent_change_1h: Double,
    percent_change_24h: Double,
    percent_change_30d: Double,
    percent_change_7d: Double
  ) -> T): Query<T>

  public fun getCurrencies(): Query<Currency>

  public fun addCurrency(currency: Currency): Unit
}
