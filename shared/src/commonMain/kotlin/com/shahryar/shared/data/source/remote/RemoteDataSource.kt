package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.Data
import com.shahryar.shared.data.model.Resource

interface RemoteDataSource {

    suspend fun getPrices(): Resource<List<Currency>>
}