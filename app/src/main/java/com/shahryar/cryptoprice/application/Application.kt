package com.shahryar.cryptoprice.application

import android.app.Application
import com.shahryar.cryptoprice.repository.UserPreferencesRepository

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        UserPreferencesRepository.getInstance(applicationContext)
    }
}