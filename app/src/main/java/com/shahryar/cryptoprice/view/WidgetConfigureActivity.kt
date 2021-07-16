package com.shahryar.cryptoprice.view

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.databinding.ActivityWidgetConfigureBinding
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.adapter.WidgetRecyclerViewAdapter
import com.shahryar.cryptoprice.updateWidget
import com.shahryar.cryptoprice.viewModel.WidgetConfigureViewModel
import com.shahryar.cryptoprice.viewModel.WidgetConfigureViewModelFactory

class WidgetConfigureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWidgetConfigureBinding
    private lateinit var viewModel: WidgetConfigureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResult(RESULT_CANCELED)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_widget_configure)
        viewModel =
            ViewModelProvider(this, WidgetConfigureViewModelFactory(applicationContext)).get(
                WidgetConfigureViewModel::class.java
            )
        binding.viewModel = viewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = WidgetRecyclerViewAdapter()

        viewModel.currencies.observe(this, {
            (binding.recyclerView.adapter as WidgetRecyclerViewAdapter).submitList(it)
        })

        setListeners()
    }

    private fun setListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        (binding.recyclerView.adapter as WidgetRecyclerViewAdapter).setOnItemClickedListener(object :
            WidgetRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClicked(item: Currency) {
                val appWidgetId = intent?.extras?.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

                if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()
                else {
                    if (Utils().writePreference(
                            applicationContext,
                            appWidgetId.toString(),
                            item.id
                        )
                    ) {
                        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
                        updateWidget(
                            appWidgetManager,
                            applicationContext,
                            intArrayOf(appWidgetId),
                            appWidgetManager.getAppWidgetInfo(appWidgetId).initialLayout
                        )
                        val resultValue = Intent().apply {
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        }
                        setResult(RESULT_OK, resultValue)
                        finish()
                    }
                }
            }
        })
    }
}