package com.shahryar.cryptoprice.model

data class Data(
    val `data`: List<DataX>,
    val status: Status
)

data class DataX(
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val max_supply: Double?,
    val name: String,
    val num_market_pairs: Int,
    val platform: Any?,
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double
)

data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: Any?,
    val notice: Any?,
    val timestamp: String,
    val total_count: Int
)

data class Quote(
    val USD: USD
)

data class USD(
    val last_updated: String,
    val market_cap: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_7d: Double,
    val price: Double,
    val volume_24h: Double
)