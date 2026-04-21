package com.chelo.splitmouse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.domain.repositories.EventRepository
import com.chelo.splitmouse.domain.repositories.ExpenseRepository
import com.chelo.splitmouse.domain.repositories.ParticipantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventDetailViewModel(
    val eventId: Long,
    val eventRepository: EventRepository,
    val participantRepository: ParticipantRepository,
    val expensesRepository: ExpenseRepository,
) : ViewModel() {


    private val event = eventRepository.getEventById(eventId)

    private val participants = participantRepository.getParticipantsByEvent(eventId)

    private val expenses = expensesRepository.getExpensesByEvent(eventId)


    val uiState: StateFlow<EventDetailState> =
        combine(event, participants, expenses) { event, participants, expenses ->
            Log.i("CHELO", expenses.toString())
            EventDetailState(
                event,
                participants = participants,
                expenses = expenses,
                isLoading = false
            )
        }.catch {
            emit(EventDetailState(event = null, isError = true, errorMessage = it.message))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(4000),
            EventDetailState(event = null, isLoading = true)
        )


    fun addParticipant(name: String) {
        viewModelScope.launch {
            try {
                participantRepository.addParticipant(Participant(0, name, eventId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addExpense(amount: Double, description: String, playerId: Long) {
        viewModelScope.launch {
            if (listOf(
                    amount,
                    description,
                    playerId
                ).any() { false }
            ) throw Exception("Invalid data")
            try {
                val expense = Expense(0, amount, description, eventId, playerId)
                Log.i("CHELO", expense.toString())
                expensesRepository.addExpense(expense)
                val event = uiState.value.event
                eventRepository.updateEvent(
                    event?.copy(totalAmount = event.totalAmount + amount) ?: return@launch
                )

            } catch (e: Exception) {
                Log.i("CHELO", e.message.toString())
                e.printStackTrace()
            }
        }
    }

    fun deleteParticipant(participantId: Long) {
        viewModelScope.launch {
            try {
                participantRepository.deleteParticipant(participantId)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("CHELO", e.message.toString())
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
    val expenses: List<Expense> = emptyList(),
)
