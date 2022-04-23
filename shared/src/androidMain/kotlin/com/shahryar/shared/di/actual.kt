package com.shahryar.shared.di

import android.preference.PreferenceManager
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.ObservableSettings
import com.shahryar.shared.CryptoPriceDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<SqlDriver> {
            val driver =
                AndroidSqliteDriver(CryptoPriceDb.Schema, get(), "cryptopricedb.db")
            driver
        }

        single<ObservableSettings> {
            AndroidSettings(
                PreferenceManager.getDefaultSharedPreferences(get())
            )
        }
    }
}