package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.model.asCurrency

class RemoteDataSourceImpl(private val priceApi: PriceApi):
    RemoteDataSource {

    override suspend fun getPrices(): Resource<List<CurrencyDto>> {
        return try {
            priceApi.getPrices().let {
                if (!it.data.isNullOrEmpty()) Resource.success(it.asCurrency())
                else Resource.error(null, it.status.error_message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            return Resource.error(null, e.message.toString())
        }
    }
}