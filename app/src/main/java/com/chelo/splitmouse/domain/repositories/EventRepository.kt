package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Event

interface EventRepository {


    suspend fun addEvent(event: Event)

    suspend fun updateEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun getAllEvents(): List<Event>

}