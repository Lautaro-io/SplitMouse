package com.chelo.splitmouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.domain.repositories.EventRepository
import com.chelo.splitmouse.domain.repositories.ParticipantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventDetailViewModel(
    val eventId: Long,
    eventRepository: EventRepository,
    val participantRepository: ParticipantRepository,
) : ViewModel() {


    private val event = eventRepository.getEventById(eventId)

    private val participants = participantRepository.getParticipantsByEvent(eventId)


    val uiState: StateFlow<EventDetailState> = combine(event, participants) { event, participants ->
        EventDetailState(event, participants = participants, isLoading = false)
    }.catch {
        emit(EventDetailState(event = null, isError = true, errorMessage = it.message))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(4000),
        EventDetailState(event = null, isLoading = true)
    )


    fun addParticipant(participant: Participant){
        viewModelScope.launch {
            try {
                participantRepository.addParticipant(participant)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

//    val uiState: StateFlow<EventDetailState> =
//        eventRepository.getEventById(eventId).map { event ->
//
//        EventDetailState(event)
//    }.catch {
//        emit(EventDetailState(event = null, isError = true, errorMessage = it.message))
//
//
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(4000),
//        initialValue = EventDetailState(event = null, isLoading = true)
//    )
//}
}

data class EventDetailState(
    val event: Event?,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val participants: List<Participant> = emptyList(),
)