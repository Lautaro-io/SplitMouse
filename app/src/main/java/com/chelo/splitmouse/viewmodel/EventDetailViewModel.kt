package com.chelo.splitmouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.repositories.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EventDetailViewModel(val eventId: Long, eventRepository: EventRepository) : ViewModel() {


    val uiState: StateFlow<EventDetailState> = eventRepository.getEventById(eventId).map { event ->
        EventDetailState(event)
    }.catch {
        emit(EventDetailState(event = null, isError = true, errorMessage = it.message))


    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(4000),
        initialValue = EventDetailState(event = null, isLoading = true)
    )
}


data class EventDetailState(
    val event: Event?,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)