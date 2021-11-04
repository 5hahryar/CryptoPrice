package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.data.model.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceApi{

    @GET("latest")
    suspend fun getPrices(
        @Query("CMC_PRO_API_KEY") key: String?,
        @Query("sort") sort: String? = "market_cap"
    ): Data
}