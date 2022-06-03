package com.shahryar.shared.data.repository


import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.CurrencyDtoMapper
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.source.local.LocalDataSource
import com.shahryar.shared.data.source.remote.RemoteDataSource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CurrencyRepositoryImpl : CurrencyRepository, KoinComponent {

    private val remoteDataSource: RemoteDataSource by inject()
    private val localDataSource: LocalDataSource by inject()

    override fun getCurrencies(fetchFromRemote: Boolean): Flow<Resource<List<Currency>>> {
        return flow {
            val currencies = try { localDataSource.getCurrencies() } catch (e: Exception) { listOf() }
            if (currencies.isNotEmpty()) {
                emit(Resource.success(currencies.map { currency -> CurrencyDtoMapper().mapTo(currency) }))
            }
            if (currencies.isNullOrEmpty() || fetchFromRemote) {
                try {
                    with(remoteDataSource.getPrices()) {
                        if (this.isNotEmpty()) {
                            localDataSource.insertCurrencies(this)
                            emit(Resource.success(this.map { currency -> CurrencyDtoMapper().mapTo(currency) }))
                        }
                    }
                } catch (e: Exception) {
                    emit(Resource.error(currencies.map { currency -> CurrencyDtoMapper().mapTo(currency) }, e.message.toString()))
                }
            } else {
                emit(Resource.success(currencies.map { currency -> CurrencyDtoMapper().mapTo(currency) }))
            }
        }
    }
}
