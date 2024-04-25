package com.nelsonxilv.gstoutimetable.presentation.screens

sealed interface TimetableUiState {
    data class Success(val lessons: String) : TimetableUiState
    object Error : TimetableUiState
    object Loading : TimetableUiState
}