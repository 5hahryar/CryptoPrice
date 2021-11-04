package com.shahryar.cryptoprice.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import com.shahryar.cryptoprice.core.common.Utils
import com.shahryar.cryptoprice.data.repository.Repository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CryptoPriceWidgetProviderSmall : AppWidgetProvider(), KoinComponent {

    private val mRepository: Repository by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mRepository)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                Utils().removePreference(context!!, appWidgetId.toString())
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}

class CryptoPriceWidgetProviderMedium : AppWidgetProvider(), KoinComponent {

    private val mRepository: Repository by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mRepository)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                Utils().removePreference(context!!, appWidgetId.toString())
            }
        }
    }
}

class CryptoPriceWidgetProviderLarge : AppWidgetProvider(), KoinComponent {

    private val mRepository: Repository by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, mRepository)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateWidget(appWidgetManager, context!!, intArrayOf(appWidgetId), mRepository)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                Utils().removePreference(context!!, appWidgetId.toString())
            }
        }
    }
}
