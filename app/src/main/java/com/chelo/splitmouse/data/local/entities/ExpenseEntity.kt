package com.chelo.splitmouse.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chelo.splitmouse.domain.model.Expense


@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ParticipantEntity::class,
            parentColumns = ["id"],
            childColumns = ["payerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("eventId"), Index("payerId")]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val description: String,
    val eventId: Long,
    val payerId: Long
)

fun ExpenseEntity.toModel() = Expense(
    id = id,
    amount = amount,
    description = description,
    eventId = eventId,
    payerId = payerId
)