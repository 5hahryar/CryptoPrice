package com.shahryar.cryptoprice.data.repository

import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.asDatabaseModel
import com.shahryar.cryptoprice.data.repository.base.LocalDataSource
import com.shahryar.cryptoprice.data.repository.base.RemoteDataSource
import com.shahryar.cryptoprice.data.repository.base.Repository

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

    override suspend fun refresh() {
        remoteDataSource.fetchPrices().let {
            localDataSource.insertAll(it.asDatabaseModel())
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
