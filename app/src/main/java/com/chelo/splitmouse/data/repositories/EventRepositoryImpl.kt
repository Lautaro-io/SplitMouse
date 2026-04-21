package com.chelo.splitmouse.data.repositories

import com.chelo.splitmouse.data.local.daos.EventDao
import com.chelo.splitmouse.data.local.entities.toModel
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.toEntity
import com.chelo.splitmouse.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl(val eventDao: EventDao) : EventRepository {
    override suspend fun addEvent(event: Event) {
        eventDao.insertEvent(event.toEntity())
    }

    override suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event.toEntity())
    }

    override suspend fun deleteEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override fun getAllEvents(): Flow<List<Event>> {
        return eventDao.getEvents().map {
            it.map { event ->
                event.toModel()
            }
        }
    }

    override fun getEventById(id: Long): Flow<Event?> {
        return eventDao.getEventById(id).map { it?.toModel() }
    }


}
