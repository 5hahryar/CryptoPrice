package com.shahryar.shared.data.model

import com.shahryar.shared.utils.formatString

class CurrencyDtoMapper : Mapper<CurrencyDto, Currency> {

    override fun mapTo(input: CurrencyDto): Currency {
        return with(input) {
            Currency(
                id,
                formatString(circulating_supply),
                cmc_rank,
                date_added,
                last_updated,
                formatString(max_supply ?: 0),
                name,
                symbol,
                formatString(total_supply),
                formatString(market_cap),
                formatString(percent_change_1h),
                formatString(percent_change_24h),
                formatString(percent_change_30d),
                formatString(percent_change_7d),
                formatString(price),
                formatString(volume_24h),
            )
        }
    }

    override fun mapFrom(input: Currency): CurrencyDto {
        TODO("Not yet implemented")
    }
}