package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Participant
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository {

    suspend fun addParticipant( participant: Participant)

    suspend fun deleteParticipant(participant: Participant)

    fun getParticipantsByEvent(eventId : Long): Flow<List<Participant>>

}