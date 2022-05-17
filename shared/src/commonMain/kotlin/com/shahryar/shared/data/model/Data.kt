package com.shahryar.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val `data`: List<DataX>?,
    var status: Status
)

@Serializable
data class DataX(
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val max_supply: Double?,
    val name: String,
    val num_market_pairs: Int,
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double
)

@Serializable
data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: String?,
    val timestamp: String,
    val total_count: Int?
)

@Serializable
data class Quote(
    val USD: USD
)

@Serializable
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

fun Data.asCurrency(): List<CurrencyDto> {
    return data!!.map {
        CurrencyDto(
            id = it.id,
            circulating_supply = it.circulating_supply,
            cmc_rank = it.cmc_rank,
            date_added = it.date_added,
            last_updated = it.last_updated,
            max_supply = it.max_supply,
            name = it.name,
            symbol = it.symbol,
            total_supply = it.total_supply,
//                        market_cap = "%,d".format(it.quote.USD.market_cap.toLong()),
            market_cap = it.quote.USD.market_cap.toString(),
            percent_change_1h = it.quote.USD.percent_change_1h,
            percent_change_24h = it.quote.USD.percent_change_24h,
            percent_change_30d = it.quote.USD.percent_change_30d,
            percent_change_7d = it.quote.USD.percent_change_7d,
            price = it.quote.USD.price,
            volume_24h = it.quote.USD.price
        )
    }
}