package com.chelo.splitmouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.repositories.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalStdlibApi::class)
class MainViewModel(private val repo: EventRepository) : ViewModel() {

    private val _uiState = repo.getAllEvents().map {
        MainUiState(events = it)
    }.catch {
        emit(MainUiState(error = it.message, isLoading = false))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = MainUiState(isLoading = true)
    )
    val uiState = _uiState




}


data class MainUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null,
)


