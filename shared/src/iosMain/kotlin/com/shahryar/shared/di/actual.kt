package com.shahryar.shared.di

import com.russhwolf.settings.ObservableSettings
import com.shahryar.shared.CryptoPriceDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import com.russhwolf.settings.AppleSettings

actual fun platformModule(): Module {
    return module {
        single<SqlDriver> {
            val driver =
                NativeSqliteDriver(CryptoPriceDb.Schema, "ccryptopricedb.db")
            driver
        }

        single<ObservableSettings> {
            AppleSettings(NSUserDefaults.standardUserDefaults, true)
        }
    }
}