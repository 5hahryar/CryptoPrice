package com.shahryar.shared.data.repository


import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencies(fetchFromRemote: Boolean): Flow<Resource<List<CurrencyDto>>>
}