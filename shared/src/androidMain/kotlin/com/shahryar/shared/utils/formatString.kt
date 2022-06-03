package com.shahryar.shared.utils

import java.lang.Exception
import java.math.BigDecimal

actual fun formatString(number: Any): String {
    return try {
        when (number) {
            is String -> {
                String.format(
                    "%,d",
                    BigDecimal(number.toString()).toBigInteger()
                )
            }
            is Double -> {
                String.format("%,.2f", number)
            }
            else -> "NaN"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "NaN"
    }
}