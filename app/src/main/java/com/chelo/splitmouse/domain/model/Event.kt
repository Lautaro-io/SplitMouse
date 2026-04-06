package com.chelo.splitmouse.domain.model

import com.chelo.splitmouse.data.local.entities.EventEntity


data class Event(
    val id: Long ,
    val name: String,
    val date: String,
    val description: String,
    val totalAmount: Double,
)

fun Event.toEntity() = EventEntity(
    id = id,
    name = name,
    date = date,
    description = description,
    totalAmount = totalAmount
)
