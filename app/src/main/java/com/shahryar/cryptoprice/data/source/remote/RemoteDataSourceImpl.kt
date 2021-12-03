package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.CryptoPriceApplication
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.model.Status
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.collect

class RemoteDataSourceImpl(private val priceApi: PriceApi, private val userPreferencesRepository: UserPreferencesRepository):
    RemoteDataSource {

    override suspend fun fetchPrices(): Resource<Data> {
        return try {
            priceApi.getPrices(userPreferencesRepository.apiKey, null).let {
                if (!it.data.isNullOrEmpty()) Resource.success(it)
                else Resource.error(null, "Error fetching data")
            }
        } catch (e: Exception) {
            return Resource.error(null, e.message.toString())
        }
    }
}