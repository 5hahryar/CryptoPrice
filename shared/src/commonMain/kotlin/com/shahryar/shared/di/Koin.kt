package com.shahryar.shared.di

import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.repository.CurrencyRepository
import com.shahryar.shared.data.repository.CurrencyRepositoryImpl
import com.shahryar.shared.data.source.local.CurrencyDaoImpl
import com.shahryar.shared.data.source.local.LocalDataSource
import com.shahryar.shared.data.source.local.LocalDataSourceImpl
import com.shahryar.shared.data.source.remote.PriceApi
import com.shahryar.shared.data.source.remote.RemoteDataSource
import com.shahryar.shared.data.source.remote.RemoteDataSourceImpl
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    Napier.d("Init koin called")
    appDeclaration()
    modules(commonModule, platformModule())
}

// Called by iOS
fun initKoin() = initKoin{}

val commonModule = module {
    single {
        CryptoPriceSettings
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(PriceApi())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(CurrencyDaoImpl(get()))
    }
    
    single<CurrencyRepository> {
        CurrencyRepositoryImpl()
    }
}