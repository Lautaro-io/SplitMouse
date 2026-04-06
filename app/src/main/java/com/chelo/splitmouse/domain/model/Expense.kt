package com.chelo.splitmouse.domain.model


data class Expense(
     val id: Long ,
    val amount: Double,
    val description: String,
    val eventId: Long,
    val payerId: Long
//    val isPaid: Boolean
)