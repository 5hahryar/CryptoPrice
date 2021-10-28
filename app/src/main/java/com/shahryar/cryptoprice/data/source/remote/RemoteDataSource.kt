package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.data.model.Data

interface RemoteDataSource {

    suspend fun fetchPrices(): Data
}