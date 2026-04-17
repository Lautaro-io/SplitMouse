package com.chelo.splitmouse.data.repositories

import com.chelo.splitmouse.data.local.daos.ParticipantDao
import com.chelo.splitmouse.data.local.entities.toModel
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.domain.model.toEntity
import com.chelo.splitmouse.domain.repositories.ParticipantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParticipantRepositoryImpl(val participantDao: ParticipantDao) : ParticipantRepository {


    override suspend fun addParticipant(participant: Participant) {
        participantDao.insertParticipant(participant.toEntity())
    }

    override suspend fun deleteParticipant(participant: Participant) {
        TODO("Not yet implemented")
    }

    override fun getParticipantsByEvent(eventId: Long): Flow<List<Participant>> {
        return participantDao.getParticipantsByEvent(eventId).map { list ->
            list.map {
                it.toModel()
            }
        }
    }
}