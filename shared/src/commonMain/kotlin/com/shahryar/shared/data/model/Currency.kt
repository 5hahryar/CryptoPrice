package com.shahryar.shared.data.model

data class Currency(
    val id: Int,
    val circulatingSupply: String,
    val cmcRank: Int,
    val dateAdded: String,
    val lastUpdated: String,
    val maxSupply: String,
    val name: String,
    val symbol: String,
    val totalSupply: String,
    val marketCap: String,
    val percentChange1h: String,
    val percentChange24h: String,
    val percentChange30d: String,
    val percentChange7d: String,
    val price: String,
    val volume24h: String
)
