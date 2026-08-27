package com.chelo.splitmouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.repositories.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEventViewModel(private val repo: EventRepository) : ViewModel() {

    private val _formState = MutableStateFlow(EventFormState())
    val formState = _formState.asStateFlow()


    val isFormValid = _formState.map { state ->
        state.name.isNotBlank() && state.date.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000L), false)


    fun addEvent(onSuccess: (Long) -> Unit) {
        val event = _formState.value
        viewModelScope.launch {
            try {
                if (!validateForm()) throw Exception("Invalid data")
                val event = Event(
                    id = 0,
                    name = event.name,
                    description = event.description ?: "",
                    date = event.date,
                )
                val generatedEvent = repo.addEvent(
                    event
                )
                onSuccess(generatedEvent)
                _formState.update {
                    EventFormState()
                }

            }catch (e : Exception){ }
        }

    }

    fun updateFormState(field: FieldType, value: String) {
        _formState.update {
            when (field) {
                FieldType.ID -> it.copy(id = value.toLong())
                FieldType.NAME -> it.copy(name = value)
                FieldType.DESCRIPTION -> it.copy(description = value)
                FieldType.DATE -> it.copy(date = value)
            }
        }
    }

    private fun resetForm(){
        _formState.update {
            EventFormState()
        }
    }
    fun validateForm(): Boolean {
        val validName = _formState.value.name.isNotBlank()
        val validDate = _formState.value.date.isNotBlank()
        return validDate && validName

    }

    fun updateEvent(onSuccess: () -> Unit) {
        val event = _formState.value
        viewModelScope.launch {
            try {
                if (!validateForm()) throw Exception("Invalid data")
                val eventToUpdate = Event(
                    id = event.id ?: 0,
                    name = event.name,
                    description = event.description ?: "",
                    date = event.date,
                )
                repo.updateEvent(eventToUpdate)
                resetForm()
                onSuccess()
            }catch (e : Exception){

            }
        }
    }


}


enum class FieldType { ID, NAME, DESCRIPTION, DATE }

data class EventFormState(
    val id : Long? = null,
    val name: String = "",
    val description: String? = null,
    val date: String = "",
)