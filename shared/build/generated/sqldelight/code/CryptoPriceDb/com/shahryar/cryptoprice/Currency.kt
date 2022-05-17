package com.shahryar.cryptoprice

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Currency(
  public val id: Long,
  public val cmc_rank: Long,
  public val name: String,
  public val symbol: String,
  public val market_cap: String,
  public val date_added: String,
  public val last_updated: String,
  public val price: Double,
  public val circulating_supply: Double,
  public val max_supply: Double?,
  public val total_supply: Double,
  public val volume_24h: Double,
  public val percent_change_1h: Double,
  public val percent_change_24h: Double,
  public val percent_change_30d: Double,
  public val percent_change_7d: Double
) {
  public override fun toString(): String = """
  |Currency [
  |  id: $id
  |  cmc_rank: $cmc_rank
  |  name: $name
  |  symbol: $symbol
  |  market_cap: $market_cap
  |  date_added: $date_added
  |  last_updated: $last_updated
  |  price: $price
  |  circulating_supply: $circulating_supply
  |  max_supply: $max_supply
  |  total_supply: $total_supply
  |  volume_24h: $volume_24h
  |  percent_change_1h: $percent_change_1h
  |  percent_change_24h: $percent_change_24h
  |  percent_change_30d: $percent_change_30d
  |  percent_change_7d: $percent_change_7d
  |]
  """.trimMargin()
}
