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

fun formatFecha(fechaInput: String): String {
    val formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val fecha = LocalDate.parse(fechaInput, formatterEntrada)

    val formatterSalida = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES"))

    return fecha.format(formatterSalida).replaceFirstChar { it.uppercase() }
}