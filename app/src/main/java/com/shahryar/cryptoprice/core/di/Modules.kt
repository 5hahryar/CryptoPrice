package com.shahryar.cryptoprice.core.di

import com.shahryar.cryptoprice.ui.prices.PriceViewModel
import com.shahryar.cryptoprice.ui.settings.SettingsViewModel
import com.shahryar.cryptoprice.ui.widgets.WidgetConfigureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { (PriceViewModel(get())) }
    viewModel { SettingsViewModel(get()) }
    viewModel { WidgetConfigureViewModel(get()) }
}

val cryptoPriceModules = listOf(mainModule)