package com.shahryar.cryptoprice.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.core.common.Utils
import com.shahryar.cryptoprice.data.repository.base.Repository
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
        updateWidget(appWidgetManager, context!!, appWidgetIds, R.layout.widget_small, mRepository)
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

class CryptoPriceWidgetProviderMedium : AppWidgetProvider(), KoinComponent {

    private val mRepository: Repository by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateWidget(appWidgetManager, context!!, appWidgetIds, R.layout.widget_medium, mRepository)
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
        updateWidget(appWidgetManager, context!!, appWidgetIds, R.layout.widget_large, mRepository)
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
