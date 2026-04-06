package com.chelo.splitmouse.domain.model

import com.chelo.splitmouse.data.local.entities.ParticipantEntity


data class Participant (
    val id : Long  ,
    val name : String,
    val eventId : Long
)


fun Participant.toEntity() = ParticipantEntity(
    id = id,
    name = name,
    eventId = eventId
)