package com.shahryar.cryptoprice.data.repository

import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.Data
import com.shahryar.cryptoprice.data.model.Resource
import com.shahryar.cryptoprice.data.model.asDatabaseModel
import com.shahryar.cryptoprice.data.source.local.LocalDataSource
import com.shahryar.cryptoprice.data.source.remote.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    Repository {

    override fun getCurrencies() =
        localDataSource.getCurrencies()


    override suspend fun insertCurrencies(currencies: List<Currency>) {
        localDataSource.insertAll(currencies)
    }

    override suspend fun refresh(): Resource<Data> {
        return remoteDataSource.fetchPrices().let { resource ->
            if (resource.status == Resource.Status.SUCCESS) localDataSource.insertAll(resource.data!!.asDatabaseModel())
            resource
        }
    }

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
}
