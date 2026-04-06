package com.chelo.splitmouse.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id : Long = 0 ,
    val name : String ,
    val date : String ,
    val description : String,
    val totalAmount : Double
)

