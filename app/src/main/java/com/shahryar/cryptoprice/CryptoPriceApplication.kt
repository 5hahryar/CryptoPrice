package com.shahryar.cryptoprice

import android.app.Application
import android.content.Context
import androidx.lifecycle.liveData
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import com.shahryar.cryptoprice.data.repository.preferences.UserPreferencesRepository
import com.shahryar.shared.di.initKoin
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoPriceApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(applicationContext)
            modules(cryptoPriceModules)
        }
    }
}