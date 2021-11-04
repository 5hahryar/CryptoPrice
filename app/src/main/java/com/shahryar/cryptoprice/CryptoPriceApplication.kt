package com.shahryar.cryptoprice

import android.app.Application
import android.content.Context
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoPriceApplication: Application() {

    companion object {
        //TODO: Fix these
        var context: Context = CryptoPriceApplication.context
        var apiKey: String = ""
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        startKoin {
            androidContext(applicationContext)
            modules(cryptoPriceModules)
        }
    }
}