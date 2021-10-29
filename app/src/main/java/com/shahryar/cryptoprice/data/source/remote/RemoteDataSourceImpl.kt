package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.CryptoPriceApplication
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.model.Status
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository

class RemoteDataSourceImpl(private val priceApi: PriceApi, userPreferencesRepository: UserPreferencesRepository):
    RemoteDataSource {

    override suspend fun fetchPrices(): Resource<Data> {
        return try {
            priceApi.getPrices(CryptoPriceApplication.apiKey, null).let {
                if (!it.data.isNullOrEmpty()) Resource.success(it)
                else Resource.error(null, "Error fetching data")
            }
        } catch (e: Exception) {
            return Resource.error(null, e.message.toString())
        }
    }
}