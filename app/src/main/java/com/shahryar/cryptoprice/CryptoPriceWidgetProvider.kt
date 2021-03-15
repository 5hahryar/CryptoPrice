package com.shahryar.cryptoprice

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.shahryar.cryptoprice.repository.Repository
import com.shahryar.cryptoprice.repository.local.getDatabase

class CryptoPriceWidgetProviderSmall: AppWidgetProvider(){
    private lateinit var repo: Repository

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_small)
        repo = Repository(getDatabase(context!!))
        repo.refreshData(context)

        repo.currencies.observeForever {
            val data = it[0]
            var priceDiff = String.format("%.2f", data.percent_change_24h)
            if (data.percent_change_24h > 0) priceDiff = "+$priceDiff"
            if (appWidgetIds != null) {
                for (appWidget in appWidgetIds) {
                    if (data.percent_change_24h > 0) {
                        remoteViews.setTextColor(
                            R.id.price_difference,
                            context.resources.getColor(R.color.green)
                        )
                    } else {
                        remoteViews.setTextColor(
                            R.id.price_difference,
                            context.resources.getColor(R.color.red)
                        )
                    }
                    remoteViews.setTextViewText(R.id.name, data.name)
                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
                    remoteViews.setTextViewText(R.id.price_difference, "$priceDiff%")
                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
                    appWidgetManager?.updateAppWidget(appWidget, remoteViews)
                }
            }
        }
    }
}

class CryptoPriceWidgetProviderMedium: AppWidgetProvider(){
    private lateinit var repo: Repository

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_medium

        )
        repo = Repository(getDatabase(context!!))
        repo.refreshData(context)

        repo.currencies.observeForever {
            val data = it[0]
            var priceDiff = String.format("%.2f", data.percent_change_24h)
            var priceDiffd7 = String.format("%.2f", data.percent_change_7d)
            if (data.percent_change_24h > 0) priceDiff = "+$priceDiff"
            if (priceDiffd7.toDouble() > 0) priceDiffd7 = "+$priceDiffd7"
            if (appWidgetIds != null) {
                for (appWidget in appWidgetIds) {
                    if (data.percent_change_24h > 0) {
                        remoteViews.setTextColor(
                            R.id.h24,
                            context.resources.getColor(R.color.green)
                        )
                    } else {
                        remoteViews.setTextColor(
                            R.id.h24,
                            context.resources.getColor(R.color.red)
                        )
                    }
                    if (data.percent_change_7d > 0) {
                        remoteViews.setTextColor(
                            R.id.d7,
                            context.resources.getColor(R.color.green)
                        )
                    } else {
                        remoteViews.setTextColor(
                            R.id.d7,
                            context.resources.getColor(R.color.red)
                        )
                    }
                    remoteViews.setTextViewText(R.id.name, data.name)
                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
                    remoteViews.setTextViewText(R.id.d7, "$priceDiffd7%")
                    remoteViews.setTextViewText(R.id.h24, "$priceDiff%")
                    appWidgetManager?.updateAppWidget(appWidget, remoteViews)
                }
            }
        }
    }
}

class CryptoPriceWidgetProviderLarge: AppWidgetProvider(){
    lateinit var repo: Repository
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_large

        )
        repo = Repository(getDatabase(context!!))
        repo.refreshData(context)

        repo.currencies.observeForever {
            val data = it[0]
            var priceDiffd7 = String.format("%.2f", data.percent_change_7d)
            var priceDiffh24 = String.format("%.2f", data.percent_change_24h)
            if (priceDiffd7.toDouble() > 0) priceDiffd7 = "+$priceDiffd7"
            if (priceDiffh24.toDouble() > 0) priceDiffh24 = "+$priceDiffh24"
            if (appWidgetIds != null) {
                for (appWidget in appWidgetIds) {
                    if (data.percent_change_24h > 0) {
                        remoteViews.setTextColor(
                            R.id.h24,
                            context.resources.getColor(R.color.green)
                        )
                    } else {
                        remoteViews.setTextColor(
                            R.id.h24,
                            context.resources.getColor(R.color.red)
                        )
                    }
                    if (data.percent_change_7d > 0) {
                        remoteViews.setTextColor(
                            R.id.d7,
                            context.resources.getColor(R.color.green)
                        )
                    } else {
                        remoteViews.setTextColor(
                            R.id.d7,
                            context.resources.getColor(R.color.red)
                        )
                    }

                    remoteViews.setTextViewText(R.id.name, data.name)
                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
                    remoteViews.setTextViewText(R.id.d7, "$priceDiffd7%")
                    remoteViews.setTextViewText(R.id.h24, "$priceDiffh24%")
                    remoteViews.setTextViewText(R.id.marketCap, "$${data.market_cap}")
                    appWidgetManager?.updateAppWidget(appWidget, remoteViews)
                }
            }
        }
    }
}