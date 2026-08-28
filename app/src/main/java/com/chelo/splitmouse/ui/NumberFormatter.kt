package com.chelo.splitmouse.ui

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


val argentinaLocale = Locale("es", "AR")
val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(argentinaLocale).apply {
    minimumFractionDigits = 0
    maximumFractionDigits = 0
}

fun Double.toArgentineCurrency(): String {
    return currencyFormatter.format(this)
}

fun formatFecha(fechaInput: String, locale: Locale = Locale.getDefault()): String {
    val formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val fecha = LocalDate.parse(fechaInput, formatterEntrada)

    val pattern = if (locale.language == "es") "EEEE, d 'de' MMMM" else "EEEE, MMMM d"
    val formatterSalida = DateTimeFormatter.ofPattern(pattern, locale)

    return fecha.format(formatterSalida).replaceFirstChar { it.uppercase() }
}