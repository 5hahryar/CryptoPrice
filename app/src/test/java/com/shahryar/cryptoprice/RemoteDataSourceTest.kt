package com.shahryar.cryptoprice

import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.source.remote.RemoteDataSource

class RemoteDataSourceTest: RemoteDataSource {

    override suspend fun fetchPrices(): Data {
        TODO("Not yet implemented")
    }
}