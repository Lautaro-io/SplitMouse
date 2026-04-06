package com.chelo.splitmouse.domain.model


data class Event(
    val id: Long ,
    val name: String,
    val date: String,
    val description: String,
    val totalAmount: Double,
)
