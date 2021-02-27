package com.shahryar.cryptoprice.model

data class Data(
        val `data`: List<String>,
        val status: Status
)

data class String(
        val circulating_supply: Double,
        val cmc_rank: Int,
        val date_added: kotlin.String,
        val id: Int,
        val last_updated: kotlin.String,
        val max_supply: Double?,
        val name: kotlin.String,
        val num_market_pairs: Int,
        val platform: Any?,
        val quote: Quote,
        val slug: kotlin.String,
        val symbol: kotlin.String,
        val tags: List<kotlin.String>,
        val total_supply: Double
)

data class Status(
        val credit_count: Int,
        val elapsed: Int,
        val error_code: Int,
        val error_message: Any?,
        val notice: Any?,
        val timestamp: kotlin.String,
        val total_count: Int
)

data class Quote(
    val USD: USD
)

data class USD(
        val last_updated: kotlin.String,
        val market_cap: Double,
        val percent_change_1h: Double,
        val percent_change_24h: Double,
        val percent_change_30d: Double,
        val percent_change_7d: Double,
        val price: Double,
        val volume_24h: Double
)