package com.chelo.splitmouse.viewmodel

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Debt
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.domain.repositories.EventRepository
import com.chelo.splitmouse.domain.repositories.ExpenseRepository
import com.chelo.splitmouse.domain.repositories.ParticipantRepository
import com.chelo.splitmouse.domain.usecases.GetEventSettlementUseCase
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
    val getEventSettlementUseCase: GetEventSettlementUseCase,
) : ViewModel() {


    private val event = eventRepository.getEventById(eventId)

    private val participants = participantRepository.getParticipantsByEvent(eventId)

    private val expenses = expensesRepository.getExpensesByEvent(eventId)


    val uiState: StateFlow<EventDetailState> =
        combine(event, participants, expenses) { event, participants, expenses ->
            val calculatedDebts = getEventSettlementUseCase(participants, expenses)
            EventDetailState(
                event,
                participants = participants,
                expenses = expenses,
                debts = calculatedDebts,
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
            if (amount <= 0 || description.isEmpty()) throw Exception("Invalid data")
            try {
                val expense = Expense(0, amount, description, eventId, playerId)
                expensesRepository.addExpense(expense)
                val event = uiState.value.event
                eventRepository.updateEvent(
                    event?.copy(totalAmount = event.totalAmount + amount) ?: return@launch
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteParticipant(participantId: Long) {
        viewModelScope.launch {
            try {
                participantRepository.deleteParticipant(participantId)
                val newTotal = expensesRepository.calculateTotalAmount(eventId)
                val event = uiState.value.event ?: return@launch
                eventRepository.updateEvent(event.copy(totalAmount = newTotal))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("CHELO", e.message.toString())
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                expensesRepository.deleteExpense(expense)
                val event = uiState.value.event ?: return@launch
                eventRepository.updateEvent(event.copy(totalAmount = event.totalAmount - expense.amount))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateExpense(expense: Expense, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                expensesRepository.updateExpense(expense)
                val newAmount = expensesRepository.calculateTotalAmount(expense.eventId)
                val eventUpdate =
                    uiState.value.event?.copy(totalAmount = newAmount) ?: return@launch
                eventRepository.updateEvent(eventUpdate)
                onSuccess()
            } catch (e: Exception) {

            }
        }

    }

}

@Immutable
data class EventDetailState(
    val event: Event?,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val debts: List<Debt> = emptyList(),
    val participants: List<Participant> = emptyList(),
    val expenses: List<Expense> = emptyList(),
)
