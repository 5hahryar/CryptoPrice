package com.shahryar.cryptoprice.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.model.asDatabaseModel
import com.shahryar.cryptoprice.repository.local.LocalDatabase
import com.shahryar.cryptoprice.repository.network.ApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class Repository(private val localDatabase: LocalDatabase) {

    val currencies: LiveData<List<Currency>> = localDatabase.currencyDao.getCurrencies()

    fun refreshData(context: Context) {
        fetchDataFromNetwork(context)
    }

    private fun fetchDataFromNetwork(context: Context, sortKey: String? = null) {
        ApiService.priceApi.getPrices(Utils().readStringPreference(context, KEY_PREFS_API_KEY), sortKey)
            .enqueue(object : retrofit2.Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if (response.isSuccessful) {
                        runBlocking { writeToLocalDatabase(response.body()!!.asDatabaseModel()) }
                    }
                }
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    TODO("Not yet implemented")
                }
        })
    }

    private suspend fun writeToLocalDatabase(data: List<Currency>) {
        withContext(Dispatchers.IO) {localDatabase.currencyDao.insertAll(data)}
    }
}
