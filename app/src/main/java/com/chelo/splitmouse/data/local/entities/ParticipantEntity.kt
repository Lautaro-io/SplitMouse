package com.chelo.splitmouse.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chelo.splitmouse.domain.model.Participant


@Entity("participants")
data class ParticipantEntity(
    @PrimaryKey(autoGenerate = true) val id : Long = 0 ,
    val name : String,
    val eventId : Long
)


fun ParticipantEntity.toModel() = Participant(
    id = id,
    name = name,
    eventId = eventId
)
