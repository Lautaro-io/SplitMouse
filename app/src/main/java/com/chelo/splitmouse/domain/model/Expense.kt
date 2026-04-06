package com.chelo.splitmouse.domain.model

import com.chelo.splitmouse.data.local.entities.ExpenseEntity


data class Expense(
     val id: Long ,
    val amount: Double,
    val description: String,
    val eventId: Long,
    val payerId: Long
//    val isPaid: Boolean
)


fun Expense.toEntity() = ExpenseEntity(
    id = id,
    amount = amount,
    description = description,
    eventId = eventId,
    payerId = payerId
)