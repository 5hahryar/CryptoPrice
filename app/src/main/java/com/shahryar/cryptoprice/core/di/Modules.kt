package com.shahryar.cryptoprice.core.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.shahryar.cryptoprice.data.repository.Repository
import com.shahryar.cryptoprice.data.repository.RepositoryImpl
import com.shahryar.cryptoprice.data.source.local.LocalDataSourceImpl
import com.shahryar.cryptoprice.data.source.local.getDatabase
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import com.shahryar.cryptoprice.data.source.remote.ApiService
import com.shahryar.cryptoprice.data.source.remote.RemoteDataSourceImpl
import com.shahryar.cryptoprice.prices.PriceViewModel
import com.shahryar.cryptoprice.settings.SettingsViewModel
import com.shahryar.cryptoprice.widgets.WidgetConfigureViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("USER_PREFERENCES")

val mainModule = module {
    single { androidApplication().dataStore }
    single { UserPreferencesRepository(get()) }
    single<Repository> {
        RepositoryImpl(
            RemoteDataSourceImpl(ApiService.priceApi, get()),
            LocalDataSourceImpl(getDatabase(androidContext()).currencyDao)
        )
    }

    viewModel { (PriceViewModel(get(), get())) }
    viewModel { SettingsViewModel(get()) }
    viewModel { WidgetConfigureViewModel(get()) }
}

val cryptoPriceModules = listOf(mainModule)