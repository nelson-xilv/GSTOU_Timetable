package com.nelsonxilv.gstoutimetable.presentation.screens

import com.nelsonxilv.gstoutimetable.data.model.Lesson

sealed interface TimetableUiState {
    data class Success(
        val date: String,
        val lessons: List<Lesson>,
        val currentWeekType: Int
    ) : TimetableUiState
    data object Error : TimetableUiState
    data object Loading : TimetableUiState
}