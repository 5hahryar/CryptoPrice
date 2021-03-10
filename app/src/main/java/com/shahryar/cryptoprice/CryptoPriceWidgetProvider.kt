package com.shahryar.cryptoprice

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase
import com.shahryar.cryptoprice.repository.network.ApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CryptoPriceWidgetProvider: AppWidgetProvider(){
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val remoteViews = RemoteViews(context?.packageName, R.layout.price_widget)

        ApiService.priceApi.getPrices(Utils().readStringPreference(context!!, KEY_PREFS_API_KEY)).enqueue(object : Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data[0]
                    remoteViews.setTextViewText(R.id.name, data?.name)
                    remoteViews.setTextViewText(R.id.symbol, data?.symbol)
                    remoteViews.setTextViewText(R.id.price_difference, data?.quote.USD.percent_change_24h.toString())
                    remoteViews.setTextViewText(R.id.price, data?.quote.USD.price.toString())
                    appWidgetManager?.updateAppWidget(appWidgetIds!![0], remoteViews)
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })

//         CoroutineScope(Dispatchers.IO).launch{
//             var data: Currency?
//             runBlocking {
//                 data = getDatabase(context!!).currencyDao.getCoin()
//             }
//
//            remoteViews.setTextViewText(R.id.name, data?.name)
//            remoteViews.setTextViewText(R.id.symbol, data?.symbol)
//            remoteViews.setTextViewText(R.id.price_difference, data?.percent_change_24h.toString())
//            remoteViews.setTextViewText(R.id.price, data?.price.toString())
//        }
    }
}