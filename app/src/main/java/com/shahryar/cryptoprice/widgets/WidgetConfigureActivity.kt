package com.shahryar.cryptoprice.widgets

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.core.common.Utils
import com.shahryar.cryptoprice.data.model.Currency
import org.koin.android.ext.android.inject

class WidgetConfigureActivity : AppCompatActivity() {

    private val viewModel: WidgetConfigureViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ComposeView(this).apply {
            setContent { ConfigureActivityView() }
        })

        setResult(RESULT_CANCELED)
    }

    @Composable
    fun ConfigureActivityView() {
        Column {
            TopAppBarView()
            ContentView()
        }
    }

    @Composable
    fun ContentView() {
        CurrencyListView(currencies = viewModel.currencies.observeAsState().value)
    }

    @Composable
    fun TopAppBarView() {
        TopAppBar(
            title = {
                Text(text = "Select Currency", color = colorResource(id = R.color.onPrimary))
            },
            backgroundColor = colorResource(id = R.color.primary),
            navigationIcon = {
                IconButton(onClick = {
                    setResult(RESULT_CANCELED)
                    finish()
                }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = colorResource(id = R.color.onPrimary)
                    )
                }
            }
        )
    }

    @Composable
    fun CurrencyListView(currencies: List<Currency>?) {
        currencies?.let {
            LazyColumn {
                items(currencies) { currency ->
                    CurrencyItemView(currency = currency) {
                        onCurrencySelected(currency)
                    }
                }
            }
        }
    }

    @Composable
    fun CurrencyItemView(currency: Currency, onItemClick: () -> Unit) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(5.dp)
                .clickable(onClick = onItemClick),
        ) {
            Text(
                modifier = Modifier.fillMaxHeight(),
                text = currency.name,
                color = colorResource(id = R.color.text_color),
                fontSize = 16.sp
            )
        }
    }

    private fun onCurrencySelected(currency: Currency) {
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()
        else {
            if (Utils().writePreference(
                    applicationContext,
                    appWidgetId.toString(),
                    currency.id
                )
            ) {
                val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
//                        updateWidget(
//                            appWidgetManager,
//                            applicationContext,
//                            intArrayOf(appWidgetId),
//                            appWidgetManager.getAppWidgetInfo(appWidgetId).initialLayout
//                        )
                val resultValue = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
                setResult(RESULT_OK, resultValue)
                finish()
            }
        }
    }
}