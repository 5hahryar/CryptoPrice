package com.shahryar.cryptoprice.model

import android.icu.text.NumberFormat

data class Data(
        val `data`: List<DataX>,
        var status: Status
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

fun Data.asDatabaseModel(): List<Currency> {
        return data.map {
                Currency(
                        id = it.id,
                        circulating_supply = it.circulating_supply,
                        cmc_rank = it.cmc_rank,
                        date_added = it.date_added,
                        last_updated = it.last_updated,
                        max_supply = it.max_supply,
                        name = it.name,
                        symbol = it.symbol,
                        total_supply = it.total_supply,
                        market_cap = "%,d".format(it.quote.USD.market_cap.toLong()),
                        percent_change_1h = it.quote.USD.percent_change_1h,
                        percent_change_24h = it.quote.USD.percent_change_24h,
                        percent_change_30d = it.quote.USD.percent_change_30d,
                        percent_change_7d = it.quote.USD.percent_change_7d,
                        price = it.quote.USD.price,
                        volume_24h = it.quote.USD.price
                )
        }
}