package com.chelo.splitmouse.data.repositories

import com.chelo.splitmouse.data.local.daos.EventDao
import com.chelo.splitmouse.data.local.entities.toModel
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.toEntity
import com.chelo.splitmouse.domain.repositories.EventRepository

class EventRepositoryImpl(val eventDao: EventDao) : EventRepository {
    override suspend fun addEvent(event: Event) {
        eventDao.insertEvent(event.toEntity())
    }

    override suspend fun updateEvent(event: Event) {
//        eventDao.(event.toEntity())
    }

    override suspend fun deleteEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllEvents(): List<Event> {
        return eventDao.getEvents().map {
            it.toModel()
        }
    }


}
