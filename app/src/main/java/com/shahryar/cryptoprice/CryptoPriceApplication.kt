package com.shahryar.cryptoprice

import android.app.Application
import android.util.Log
import com.shahryar.cryptoprice.core.di.cryptoPriceModules
import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.di.initKoin
import org.koin.android.ext.koin.androidContext

open class CryptoPriceApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(applicationContext)
            modules(cryptoPriceModules)
        }

        CryptoPriceSettings.observeToken { token ->
            Log.d("OBS", "is empty: ${token.isNullOrEmpty()}")
        }
    }
}