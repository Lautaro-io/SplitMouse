package com.chelo.splitmouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.data.repositories.EventRepositoryImpl
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.repositories.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: EventRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState())
    val uiState = _uiState


    init {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val events = repo.getAllEvents()
            _uiState.value = _uiState.value.copy(isLoading = false , events = events)
        }

    }
    fun addEvent(event: Event) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            repo.addEvent(event)
            val events = repo.getAllEvents()
            _uiState.value = _uiState.value.copy(isLoading = false , events = events)
        }
    }


}


data class MainUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null,
)


