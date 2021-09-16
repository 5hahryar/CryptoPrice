package com.shahryar.cryptoprice.core.common

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun Double.toSeparateMoney(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(BigDecimal(this)).toString()
}