package com.shahryar.cryptoprice.repository.remote

import com.shahryar.cryptoprice.model.Data

interface RemoteDataSource {

    suspend fun fetchPrices(): Data
}