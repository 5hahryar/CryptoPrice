package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.model.asCurrency


class RemoteDataSourceImpl(private val priceApi: PriceApi):
    RemoteDataSource {

    override suspend fun getPrices(): Resource<List<Currency>> {
        return try {
            priceApi.getPrices().let {
                if (!it.data.isNullOrEmpty()) Resource.success(it.asCurrency())
                else Resource.error(null, "Error fetching data")
            }
        } catch (e: Exception) {
            return Resource.error(null, e.message.toString())
        }
    }
}

//7a2475b0-df52-4f21-be2e-a63466ad1f06