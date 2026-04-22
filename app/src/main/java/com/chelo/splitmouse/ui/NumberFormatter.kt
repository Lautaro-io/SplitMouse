package com.chelo.splitmouse.ui

import java.text.NumberFormat
import java.util.Locale


val argentinaLocale = Locale("es", "AR")
val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(argentinaLocale).apply {
    minimumFractionDigits = 0
    maximumFractionDigits = 2
}

fun Double.toArgentineCurrency(): String {
    return currencyFormatter.format(this)
}