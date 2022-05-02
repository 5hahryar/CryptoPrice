package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource

interface RemoteDataSource {

    suspend fun getPrices(): Resource<List<CurrencyDto>>
}