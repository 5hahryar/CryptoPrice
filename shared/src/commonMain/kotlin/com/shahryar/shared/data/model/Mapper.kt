package com.shahryar.shared.data.model

import com.shahryar.cryptoprice.Currency

fun Currency.mapToCurrencyDto(): CurrencyDto {
    return CurrencyDto(
        id.toInt(),
        circulating_supply,
        cmc_rank.toInt(),
        date_added,
        last_updated,
        max_supply,
        name,
        symbol,
        total_supply,
        market_cap,
        percent_change_1h,
        percent_change_24h,
        percent_change_30d,
        percent_change_7d,
        price,
        volume_24h
    )
}

fun CurrencyDto.mapToCurrency(): Currency {
    return Currency(
        id.toLong(),
        cmc_rank.toLong(),
        name,
        symbol,
        market_cap,
        date_added,
        last_updated,
        price,
        circulating_supply,
        max_supply,
        total_supply,
        volume_24h,
        percent_change_1h,
        percent_change_24h,
        percent_change_30d,
        percent_change_7d
    )
}