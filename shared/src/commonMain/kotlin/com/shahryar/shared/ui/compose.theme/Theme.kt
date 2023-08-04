package com.shahryar.shared.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CryptoPriceTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = if (isSystemInDarkTheme()) DarkColors else LightColors, content = content)
}

private val LightColors = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    primaryVariant = Color(247, 247, 247)
)

private val DarkColors = darkColors(
    primary = Color(25, 25, 25),
    onPrimary = Color.White,
    primaryVariant = Color(39, 39, 39, 255)
)