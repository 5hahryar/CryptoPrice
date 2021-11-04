package com.shahryar.cryptoprice.data.source.remote

import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource

interface RemoteDataSource {

    suspend fun fetchPrices(): Resource<Data>
}