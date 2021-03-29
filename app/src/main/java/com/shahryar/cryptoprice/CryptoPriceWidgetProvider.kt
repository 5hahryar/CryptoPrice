package com.shahryar.cryptoprice

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import com.shahryar.cryptoprice.application.Utils

class CryptoPriceWidgetProviderSmall : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context, appWidgetIds, R.layout.widget_small)
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

class CryptoPriceWidgetProviderMedium : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context, appWidgetIds, R.layout.widget_medium)
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

class CryptoPriceWidgetProviderLarge : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context, appWidgetIds, R.layout.widget_large)
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
