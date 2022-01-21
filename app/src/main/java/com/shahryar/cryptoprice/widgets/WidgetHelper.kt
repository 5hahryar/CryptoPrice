package com.shahryar.cryptoprice.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.core.common.Utils
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


fun updateWidget(
    appWidgetManager: AppWidgetManager?,
    context: Context,
    appWidgetIds: IntArray?,
    repo: Repository
) {
    if (appWidgetIds != null) {
        for (id in appWidgetIds) {
            val options = appWidgetManager?.getAppWidgetOptions(id)
            val minWidth = options?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
            minWidth?.let {
                when (it) {
                    in 115..229 -> {
                        updateWidgetSmall(context, intArrayOf(id), appWidgetManager, repo)
                    }
                    in 230..344 -> {
                        updateWidgetMedium(context, intArrayOf(id), appWidgetManager, repo)
                    }
                    else -> {
                        updateWidgetLarge(context, intArrayOf(id), appWidgetManager, repo)
                    }
                }
            }
        }
    }
}

private fun updateWidgetLarge(
    context: Context,
    appWidgetIds: IntArray?,
    appWidgetManager: AppWidgetManager?,
    repo: Repository
) {
    val remoteViews = RemoteViews(context.packageName, R.layout.widget_large)
    val pendingIntent = Intent(context, MainActivity::class.java).let {
        PendingIntent.getActivity(context, 0, it, 0)
    }
    GlobalScope.launch {
//        repo.getCurrencies().let {
//            var data = it[0]
//            var priceDiffd7 = String.format("%.2f", data.percent_change_7d)
//            var priceDiffh24 = String.format("%.2f", data.percent_change_24h)
//            if (priceDiffd7.toDouble() > 0) priceDiffd7 = "+$priceDiffd7"
//            if (priceDiffh24.toDouble() > 0) priceDiffh24 = "+$priceDiffh24"
//            if (appWidgetIds != null) {
//                for (appWidgetId in appWidgetIds) {
//                    val currencyId =
//                        Utils().readStringPreferenceInt(context, appWidgetId.toString())
//                    for (item in it) {
//                        if (item.id == currencyId) {
//                            data = item
//                        }
//                    }
//                    if (data.percent_change_24h > 0) {
//                        remoteViews.setTextColor(
//                            R.id.h24,
//                            context.resources.getColor(R.color.green)
//                        )
//                    } else {
//                        remoteViews.setTextColor(
//                            R.id.h24,
//                            context.resources.getColor(R.color.red)
//                        )
//                    }
//                    if (data.percent_change_7d > 0) {
//                        remoteViews.setTextColor(
//                            R.id.d7,
//                            context.resources.getColor(R.color.green)
//                        )
//                    } else {
//                        remoteViews.setTextColor(
//                            R.id.d7,
//                            context.resources.getColor(R.color.red)
//                        )
//                    }
//
//                    remoteViews.setTextViewText(R.id.name, data.name)
//                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
//                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
//                    remoteViews.setTextViewText(R.id.d7, "$priceDiffd7%")
//                    remoteViews.setTextViewText(R.id.h24, "$priceDiffh24%")
//                    remoteViews.setTextViewText(R.id.marketCap, "$${data.market_cap}")
//                    remoteViews.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent)
//                    appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)
//                }
//            }
//        }
    }
}

private fun updateWidgetMedium(
    context: Context,
    appWidgetIds: IntArray?,
    appWidgetManager: AppWidgetManager?,
    repo: Repository
) {
    val remoteViews = RemoteViews(context?.packageName, R.layout.widget_medium)
    val pendingIntent = Intent(context, MainActivity::class.java).let {
        PendingIntent.getActivity(context, 0, it, 0)
    }
//    GlobalScope.launch {
//        repo.getCurrencies().let {
//            var data = it[0]
//            var priceDiff = String.format("%.2f", data.percent_change_24h)
//            var priceDiffd7 = String.format("%.2f", data.percent_change_7d)
//            if (data.percent_change_24h > 0) priceDiff = "+$priceDiff"
//            if (priceDiffd7.toDouble() > 0) priceDiffd7 = "+$priceDiffd7"
//            if (appWidgetIds != null) {
//                for (appWidgetId in appWidgetIds) {
//                    val currencyId =
//                        Utils().readStringPreferenceInt(context, appWidgetId.toString())
//                    for (item in it) {
//                        if (item.id == currencyId) {
//                            data = item
//                        }
//                    }
//                    if (data.percent_change_24h > 0) {
//                        remoteViews.setTextColor(
//                            R.id.h24,
//                            context.resources.getColor(R.color.green)
//                        )
//                    } else {
//                        remoteViews.setTextColor(
//                            R.id.h24,
//                            context.resources.getColor(R.color.red)
//                        )
//                    }
//                    if (data.percent_change_7d > 0) {
//                        remoteViews.setTextColor(
//                            R.id.d7,
//                            context.resources.getColor(R.color.green)
//                        )
//                    } else {
//                        remoteViews.setTextColor(
//                            R.id.d7,
//                            context.resources.getColor(R.color.red)
//                        )
//                    }
//                    remoteViews.setTextViewText(R.id.name, data.name)
//                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
//                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
//                    remoteViews.setTextViewText(R.id.d7, "$priceDiffd7%")
//                    remoteViews.setTextViewText(R.id.h24, "$priceDiff%")
//                    remoteViews.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent)
//                    appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)
//                }
//            }
//        }
//    }
}

private fun updateWidgetSmall(
    context: Context,
    appWidgetIds: IntArray?,
    appWidgetManager: AppWidgetManager?,
    repo: Repository
) {
    val remoteViews = RemoteViews(context?.packageName, R.layout.widget_small)
    val pendingIntent = Intent(context, MainActivity::class.java).let {
        PendingIntent.getActivity(context, 0, it, 0)
    }

//    GlobalScope.launch {
//        repo.getCurrencies().let {
//            var data = it[0]
//            if (appWidgetIds != null) {
//                for (appWidgetId in appWidgetIds) {
//                    val currencyId =
//                        Utils().readStringPreferenceInt(context, appWidgetId.toString())
//                    for (item in it) {
//                        if (item.id == currencyId) {
//                            data = item
//                        }
//                    }
//                    var priceDiff = String.format("%.2f", data.percent_change_24h)
//                    if (data.percent_change_24h > 0) priceDiff = "+$priceDiff"
//                    if (data.percent_change_24h > 0) {
//                        remoteViews.setTextColor(
//                            R.id.price_difference,
//                            context.resources.getColor(R.color.green)
//                        )
//                    } else {
//                        remoteViews.setTextColor(
//                            R.id.price_difference,
//                            context.resources.getColor(R.color.red)
//                        )
//                    }
//                    remoteViews.setTextViewText(R.id.name, data.name)
//                    remoteViews.setTextViewText(R.id.symbol, data.symbol)
//                    remoteViews.setTextViewText(R.id.price_difference, "$priceDiff%")
//                    remoteViews.setTextViewText(R.id.price, "$${data.price}")
//                    remoteViews.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent)
//                    appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)
//                }
//            }
//        }
//    }
}
