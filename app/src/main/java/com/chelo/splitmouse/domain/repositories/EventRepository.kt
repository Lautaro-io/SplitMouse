package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {


    suspend fun addEvent(event: Event)

    suspend fun updateEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    fun getAllEvents(): Flow<List<Event>>


    fun getEventById(id: Long): Flow<Event?>
}



