//
//  CurrencyExt.swift
//  iosApp
//
//  Created by Shahryar on 5/6/22.
//

import Foundation
import shared

func getFakeCurrency() -> CurrencyDto {
    return CurrencyDto(id: 1, circulating_supply: 234987460, cmc_rank: 1, date_added: "date Added", last_updated: "last Updated", max_supply: 15000000, name: "Bitcoin", symbol: "BTC", total_supply: 200000000, market_cap: "120000000", percent_change_1h: 12.43, percent_change_24h: 2.33, percent_change_30d: -34.54, percent_change_7d: -3.34537, price: 38346, volume_24h: 230000453)
}
