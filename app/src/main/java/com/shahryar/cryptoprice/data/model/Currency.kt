package com.shahryar.cryptoprice.data.model

import android.icu.text.NumberFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey
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
