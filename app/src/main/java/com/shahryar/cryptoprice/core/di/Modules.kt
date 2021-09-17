package com.shahryar.cryptoprice.core.di

import com.shahryar.cryptoprice.data.repository.base.Repository
import com.shahryar.cryptoprice.data.repository.RepositoryImpl
import com.shahryar.cryptoprice.data.repository.local.LocalDataSourceImpl
import com.shahryar.cryptoprice.data.repository.local.getDatabase
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import com.shahryar.cryptoprice.data.repository.remote.ApiService
import com.shahryar.cryptoprice.data.repository.remote.RemoteDataSourceImpl
import com.shahryar.cryptoprice.viewModel.PriceViewModel
import com.shahryar.cryptoprice.viewModel.SettingsViewModel
import com.shahryar.cryptoprice.viewModel.WidgetConfigureViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<Repository> {
        RepositoryImpl(
            RemoteDataSourceImpl(ApiService.priceApi, UserPreferencesRepository.getInstance(androidContext())),
            LocalDataSourceImpl(getDatabase(androidContext()).currencyDao)
        )
    }
    single { UserPreferencesRepository(androidContext()) }

    viewModel { (PriceViewModel(UserPreferencesRepository(androidContext()), get())) }
    viewModel { SettingsViewModel(get()) }
    viewModel { WidgetConfigureViewModel(get()) }
}

val cryptoPriceModules = listOf(mainModule)