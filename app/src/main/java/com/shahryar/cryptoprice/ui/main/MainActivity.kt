package com.shahryar.cryptoprice.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.decompose.defaultComponentContext
import com.shahryar.cryptoprice.ui.CryptoPriceTheme
import com.shahryar.cryptoprice.ui.prices.PriceScreen
import com.shahryar.cryptoprice.ui.settings.SettingsScreen
import com.shahryar.shared.ui.CryptoPriceApp
import com.shahryar.shared.ui.DefaultRootComponent

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root =
            DefaultRootComponent(
                componentContext = defaultComponentContext(),
            )

        setContent {
            CryptoPriceApp(root)
        }
    }
}