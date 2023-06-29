package com.shahryar.shared.utils

import io.github.aakira.napier.Napier
import kotlinx.cinterop.cstr
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat


actual fun formatString(number: Any): String =
    try {
        when (number) {
            is String -> {
                NSString.stringWithFormat("%g", NSDecimalNumber.decimalNumberWithString(number).stringValue.cstr)
            }
            is Double -> {
                NSString.stringWithFormat("%.02f", number.toDouble())
            }
            else -> "NaN"
        }
    } catch (e: Exception) {
        Napier.d("exception for : $number")
        e.printStackTrace()
        "NaN"
    }