package com.shahryar.cryptoprice.ui.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.repository.CurrencyRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CryptoPriceWidgetProviderSmall : AppWidgetProvider(), KoinComponent {

    private val mCurrencyRepository: CurrencyRepository by inject()
    private val appSettings: CryptoPriceSettings by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mCurrencyRepository)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                appSettings.deleteSetting(appWidgetId.toString())
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mCurrencyRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}

class CryptoPriceWidgetProviderMedium : AppWidgetProvider(), KoinComponent {

    private val mCurrencyRepository: CurrencyRepository by inject()
    private val appSettings: CryptoPriceSettings by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mCurrencyRepository)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mCurrencyRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                appSettings.deleteSetting(appWidgetId.toString())
            }
        }
    }
}

class CryptoPriceWidgetProviderLarge : AppWidgetProvider(), KoinComponent {

    private val mCurrencyRepository: CurrencyRepository by inject()
    private val appSettings: CryptoPriceSettings by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mCurrencyRepository)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mCurrencyRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                appSettings.deleteSetting(appWidgetId.toString())
            }
        }
    }
}
