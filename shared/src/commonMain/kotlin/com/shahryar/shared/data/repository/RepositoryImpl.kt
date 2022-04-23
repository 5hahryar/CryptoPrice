package com.shahryar.shared.data.repository


import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.Data
import com.shahryar.shared.data.model.Resource
import com.shahryar.shared.data.source.local.LocalDataSource
import com.shahryar.shared.data.source.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource
) :
    Repository {

//    override fun getCurrencies(): Flow<List<Currency>> =
//        localDataSource.getCurrencies()
//
//    override suspend fun insertCurrencies(currencies: List<Currency>) {
//        localDataSource.insertAll(currencies)
//    }

//    @Throws(Exception::class)
//    override suspend fun refresh(): Resource<List<Currency>> {
//        return remoteDataSource.fetchPrices()
//    }

//    val currencies: LiveData<List<Currency>> = localDataSource.currencyDao.getCurrencies()
//    val currenciesByName: LiveData<List<Currency>> = localDatabase.currencyDao.getCurrenciesByName()
//    val currenciesByPrice: LiveData<List<Currency>> = localDatabase.currencyDao.getCurrenciesByPrice()
//    private var refreshListener: OnRefreshChangeListener? = null
//

//


//
//    fun setOnRefreshChangeListener(listener: OnRefreshChangeListener) {
//        this.refreshListener = listener
//    }

    interface OnRefreshChangeListener {
        fun onRefreshChanged(isRefreshing: Boolean)
    }

    override fun getCurrencies(): Flow<Resource<List<Currency>>> {
        return flow { emit(remoteDataSource.getPrices()) }.flowOn(Dispatchers.Default)
    }

}
