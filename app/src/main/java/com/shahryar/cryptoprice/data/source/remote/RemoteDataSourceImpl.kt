package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.CryptoPriceApplication
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository

class RemoteDataSourceImpl(private val priceApi: PriceApi, userPreferencesRepository: UserPreferencesRepository):
    RemoteDataSource {

    override suspend fun fetchPrices(): Data =
        priceApi.getPrices(CryptoPriceApplication.apiKey, null)
}