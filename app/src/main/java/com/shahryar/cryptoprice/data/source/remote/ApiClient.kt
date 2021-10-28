package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(getOkhttpClient())
    .build()

fun getOkhttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        client.addInterceptor(httpInterceptor)
    }

    return client.build()
}

object ApiService {
    val priceApi: PriceApi by lazy {
        retrofit.create(PriceApi::class.java)
    }
}


