package com.shahryar.cryptoprice.repository.remote

import androidx.lifecycle.asLiveData
import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.repository.base.RemoteDataSource
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository

class RemoteDataSourceImpl(private val priceApi: PriceApi, userPreferencesRepository: UserPreferencesRepository):
    RemoteDataSource {

    private lateinit var apiKey: String

    init {
        userPreferencesRepository.readOutFromDataStore.asLiveData().observeForever {
            apiKey = it
        }
    }

    override suspend fun fetchPrices(): Data =
        priceApi.getPrices(apiKey, null)
}