package com.shahryar.cryptoprice.view

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.core.common.Utils
import com.shahryar.cryptoprice.databinding.ActivityWidgetConfigureBinding
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.view.adapter.WidgetRecyclerViewAdapter
import com.shahryar.cryptoprice.viewModel.WidgetConfigureViewModel
import kotlinx.android.synthetic.main.activity_widget_configure.*
import kotlinx.android.synthetic.main.activity_widget_configure.topAppBar
import org.koin.android.ext.android.inject

class WidgetConfigureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWidgetConfigureBinding
    private val viewModel: WidgetConfigureViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResult(RESULT_CANCELED)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_widget_configure)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        recyclerView.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = WidgetRecyclerViewAdapter()

        viewModel.currencies.observe(this, {
            (recyclerView.adapter as WidgetRecyclerViewAdapter).submitList(it)
        })

        setListeners()
    }

    private fun setListeners() {
        topAppBar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        (recyclerView.adapter as WidgetRecyclerViewAdapter).setOnItemClickedListener(object :
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
        })
    }
}