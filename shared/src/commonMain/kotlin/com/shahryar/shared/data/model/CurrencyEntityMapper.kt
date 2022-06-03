package com.shahryar.shared.data.model

import com.shahryar.cryptoprice.CurrencyEntity

class CurrencyEntityMapper : Mapper<CurrencyEntity, CurrencyDto> {

    override fun mapTo(input: CurrencyEntity): CurrencyDto {
        return with(input) {
            CurrencyDto(
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
    }

    override fun mapFrom(input: CurrencyDto): CurrencyEntity {
        return with(input) {
            CurrencyEntity(
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
    }
}