package com.shahryar.cryptoprice.repository.network

import com.shahryar.cryptoprice.model.Data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    interface PriceApi{
        @GET("latest")
        fun getPrices(
                @Query("CMC_PRO_API_KEY") key: String?,
                @Query("sort") sort: String? = "market_cap"
        ): Call<Data>
    }

    object ApiService {
        val priceApi : PriceApi by lazy {
            retrofit.create(PriceApi::class.java)
        }
    }


