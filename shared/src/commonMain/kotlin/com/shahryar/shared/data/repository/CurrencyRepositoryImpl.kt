package com.shahryar.shared.data.repository


import com.shahryar.shared.data.model.CurrencyDto
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.source.local.LocalDataSource
import com.shahryar.shared.data.source.remote.RemoteDataSource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CurrencyRepositoryImpl : CurrencyRepository, KoinComponent {

    private val remoteDataSource: RemoteDataSource by inject()
    private val localDataSource: LocalDataSource by inject()

    override fun getCurrencies(fetchFromRemote: Boolean): Flow<Resource<List<CurrencyDto>>> {
        return flow {
            val currencies = localDataSource.getCurrencies()
            if (currencies.data?.isNotEmpty() == true) {
                emit(Resource.success(currencies.data))
            }
            if (currencies.data.isNullOrEmpty() || fetchFromRemote) {
                with(remoteDataSource.getPrices()) {
                    if (status == Resource.Status.SUCCESS && data != null) {
                        localDataSource.insertCurrencies(data)
                        emit(this)
                    } else {
                        emit(Resource.error(currencies.data, message.toString()))
                    }
                }
            } else {
                emit(currencies)
            }
        }
    }
}
