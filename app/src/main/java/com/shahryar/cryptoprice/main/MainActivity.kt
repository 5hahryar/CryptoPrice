package com.shahryar.cryptoprice.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahryar.cryptoprice.prices.PriceScreen
import com.shahryar.cryptoprice.settings.SettingsScreen

class MainActivity: AppCompatActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                NavHost(navController = navController, startDestination = "priceScreen") {
                    composable("priceScreen") { PriceScreen(navController) }
                    composable("settingsScreen") { SettingsScreen(navController) }
                }
            }
        }
    }
}