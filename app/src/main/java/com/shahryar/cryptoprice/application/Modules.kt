package com.shahryar.cryptoprice.application

import com.shahryar.cryptoprice.repository.base.Repository
import com.shahryar.cryptoprice.repository.RepositoryImpl
import com.shahryar.cryptoprice.repository.local.LocalDataSourceImpl
import com.shahryar.cryptoprice.repository.local.getDatabase
import com.shahryar.cryptoprice.repository.preferences.UserPreferencesRepository
import com.shahryar.cryptoprice.repository.remote.ApiService
import com.shahryar.cryptoprice.repository.remote.RemoteDataSourceImpl
import com.shahryar.cryptoprice.viewModel.PriceViewModel
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

    viewModel { (PriceViewModel(androidContext(), get())) }
}

val cryptoPriceModules = listOf(mainModule)