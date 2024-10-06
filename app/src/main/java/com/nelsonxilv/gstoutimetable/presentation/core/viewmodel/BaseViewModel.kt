package com.nelsonxilv.gstoutimetable.presentation.core.viewmodel

import androidx.lifecycle.ViewModel
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiEvent
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState, Event : UiEvent>(
    initialState: State
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    protected val currentState: State
        get() = uiState.value

    abstract fun handleEvent(event: Event)

    protected fun setState(newState: State) {
        _uiState.value = newState
    }

}