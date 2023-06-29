package com.shahryar.cryptoprice.core.di

import com.shahryar.cryptoprice.ui.widgets.WidgetConfigureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { WidgetConfigureViewModel(get()) }
}

val cryptoPriceModules = listOf(mainModule)