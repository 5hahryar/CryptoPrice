package com.shahryar.cryptoprice.data.repository.base

import com.shahryar.cryptoprice.data.model.Data

interface RemoteDataSource {

    suspend fun fetchPrices(): Data
}