package com.shahryar.cryptoprice.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CryptoPriceTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors, content = content)
}

private val LightColors = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
//    primaryVariant = Color(247, 247, 247)
)

private val DarkColors = darkColorScheme(
    primary = Color(25, 25, 25),
    onPrimary = Color.White,
//    primaryVariant = Color(39, 39, 39, 255)
)