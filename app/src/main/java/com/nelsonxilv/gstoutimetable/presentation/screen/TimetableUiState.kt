package com.nelsonxilv.gstoutimetable.presentation.screen

import com.nelsonxilv.gstoutimetable.domain.entity.Lesson

sealed interface TimetableUiState {

    data class Success(
        val date: String,
        val lessons: List<Lesson>,
        val currentWeekType: Int,
        val selectedSubgroupNumber: Int,
        val currentGroup: String
    ) : TimetableUiState

    data object Error : TimetableUiState
    data object Loading : TimetableUiState
    data object EmptyTimetable : TimetableUiState
    data object Greeting : TimetableUiState
}