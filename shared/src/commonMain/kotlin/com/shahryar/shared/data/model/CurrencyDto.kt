package com.shahryar.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    val id: Int,
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val last_updated: String,
    val max_supply: Double?,
    val name: String,
    val symbol: String,
    val total_supply: Double,
    val market_cap: String,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_7d: Double,
    val price: Double,
    val volume_24h: Double
)
