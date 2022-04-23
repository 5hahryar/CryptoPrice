package com.shahryar.cryptoprice

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Currency(
  public val id: Long,
  public val name: String,
  public val symbol: String,
  public val market_cap: String,
  public val price: Double,
  public val percent_change_1h: Double,
  public val percent_change_24h: Double,
  public val percent_change_30d: Double,
  public val percent_change_7d: Double
) {
  public override fun toString(): String = """
  |Currency [
  |  id: $id
  |  name: $name
  |  symbol: $symbol
  |  market_cap: $market_cap
  |  price: $price
  |  percent_change_1h: $percent_change_1h
  |  percent_change_24h: $percent_change_24h
  |  percent_change_30d: $percent_change_30d
  |  percent_change_7d: $percent_change_7d
  |]
  """.trimMargin()
}
