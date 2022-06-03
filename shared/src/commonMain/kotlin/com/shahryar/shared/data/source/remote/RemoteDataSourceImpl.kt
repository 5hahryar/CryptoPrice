package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.model.asCurrency

class RemoteDataSourceImpl(private val priceApi: PriceApi):
    RemoteDataSource {

    override suspend fun getPrices(): List<CurrencyDto> = priceApi.getPrices().asCurrency()
}