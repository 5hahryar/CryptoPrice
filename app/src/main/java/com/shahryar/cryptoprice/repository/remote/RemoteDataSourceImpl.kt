package com.shahryar.cryptoprice.repository.remote

import com.shahryar.cryptoprice.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RemoteDataSourceImpl(private val priceApi: PriceApi): RemoteDataSource {

    override suspend fun fetchPrices(): Data =
        priceApi.getPrices("API_KEY", null)


}