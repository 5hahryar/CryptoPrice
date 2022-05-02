package com.shahryar.cryptoprice

import android.app.Application
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import com.shahryar.shared.di.initKoin
import org.koin.android.ext.koin.androidContext

class CryptoPriceApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(applicationContext)
            modules(cryptoPriceModules)
        }
    }
}