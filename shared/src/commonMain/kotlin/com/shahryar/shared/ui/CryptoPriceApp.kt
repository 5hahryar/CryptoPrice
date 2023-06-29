package com.shahryar.shared.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.ScaleTransition
import com.shahryar.shared.ui.compose.theme.CryptoPriceTheme
import com.shahryar.shared.ui.screens.prices.PricesScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CryptoPriceApp() {
    CryptoPriceTheme {
        BottomSheetNavigator {
            Navigator(PricesScreen) {
                ScaleTransition(it)
            }
        }
    }
}