package com.shahryar.cryptoprice.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahryar.cryptoprice.ui.CryptoPriceTheme
import com.shahryar.cryptoprice.ui.prices.PriceScreen
import com.shahryar.cryptoprice.ui.settings.SettingsScreen

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            CryptoPriceTheme {
                NavHost(navController = navController, startDestination = "priceScreen") {
                    composable("priceScreen") { PriceScreen(navController) }
                    composable("settingsScreen") { SettingsScreen(navController) }
                }
            }
        }
    }
}