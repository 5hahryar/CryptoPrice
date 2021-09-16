package com.shahryar.cryptoprice

import android.app.Application
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoPriceApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(cryptoPriceModules)
        }
    }
}