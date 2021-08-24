package com.shahryar.cryptoprice.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.model.asDatabaseModel
import com.shahryar.cryptoprice.repository.local.LocalDataSource
import com.shahryar.cryptoprice.repository.local.LocalDatabase
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository
import com.shahryar.cryptoprice.repository.remote.ApiService
import com.shahryar.cryptoprice.repository.remote.RemoteDataSource
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource): Repository {

    override suspend fun getCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrencies(currencies: List<Currency>) {
        localDataSource.insertAll(currencies)
    }

//    val currencies: LiveData<List<Currency>> = localDataSource.currencyDao.getCurrencies()
//    val currenciesByName: LiveData<List<Currency>> = localDatabase.currencyDao.getCurrenciesByName()
//    val currenciesByPrice: LiveData<List<Currency>> = localDatabase.currencyDao.getCurrenciesByPrice()
//    private var refreshListener: OnRefreshChangeListener? = null
//    private var apiKey = ""
//
//    fun refreshData(context: Context) {
//        refreshListener?.onRefreshChanged(true)
//        UserPreferencesRepository.getInstance(context).readOutFromDataStore.asLiveData().observeForever {
//            apiKey = it
//            if (apiKey.isNotEmpty()) fetchDataFromNetwork(context)
//        }
//        if (apiKey.isNotEmpty()) fetchDataFromNetwork(context)
//    }
//
//    private fun fetchDataFromNetwork(context: Context, sortKey: String? = null) {
//        ApiService.priceApi.getPrices(
//            apiKey,
//            sortKey
//        )
//            .enqueue(object : retrofit2.Callback<Data> {
//                override fun onResponse(call: Call<Data>, response: Response<Data>) {
//                    if (response.isSuccessful) {
//                        runBlocking { writeToLocalDatabase(response.body()!!.asDatabaseModel()) }
//                    } else Toast.makeText(context, "Error code ${response.code()}", Toast.LENGTH_LONG).show()
//                    refreshListener?.onRefreshChanged(false)
//                }
//
//                override fun onFailure(call: Call<Data>, t: Throwable) {
//                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
//                    refreshListener?.onRefreshChanged(false)
//                }
//            })
//    }
//
//    private suspend fun writeToLocalDatabase(data: List<Currency>) {
//        withContext(Dispatchers.IO) {localDatabase.currencyDao.insertAll(data)}
//    }
//
//    fun setOnRefreshChangeListener(listener: OnRefreshChangeListener) {
//        this.refreshListener = listener
//    }

    interface OnRefreshChangeListener {
        fun onRefreshChanged(isRefreshing: Boolean)
    }
}
