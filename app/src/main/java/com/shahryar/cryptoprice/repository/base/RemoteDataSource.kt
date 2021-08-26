package com.shahryar.cryptoprice.repository.base

import com.shahryar.cryptoprice.model.Data

interface RemoteDataSource {

    suspend fun fetchPrices(): Data
}