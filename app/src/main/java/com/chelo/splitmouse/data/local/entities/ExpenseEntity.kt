package com.chelo.splitmouse.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chelo.splitmouse.domain.model.Expense


@Entity("expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val description: String,
    val eventId: Long,
    val payerId: Long
//    val isPaid: Boolean
)

fun ExpenseEntity.toModel() = Expense(
    id = id,
    amount = amount,
    description = description,
    eventId = eventId,
    payerId = payerId
)