package com.shahryar.cryptoprice

import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.repository.base.RemoteDataSource

class RemoteDataSourceTest: RemoteDataSource {

    override suspend fun fetchPrices(): Data {
        TODO("Not yet implemented")
    }
}