package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Participant

interface ParticipantRepository {

    suspend fun addParticipant( participant: Participant)

    suspend fun deleteParticipant(participant: Participant)

    suspend fun getAllParticipants(): List<Participant>

}