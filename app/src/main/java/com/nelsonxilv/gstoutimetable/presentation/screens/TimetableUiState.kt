package com.nelsonxilv.gstoutimetable.presentation.screens

sealed interface TimetableUiState {
    data class Success(val date: String, val lessons: String) : TimetableUiState
    data object Error : TimetableUiState
    data object Loading : TimetableUiState
}