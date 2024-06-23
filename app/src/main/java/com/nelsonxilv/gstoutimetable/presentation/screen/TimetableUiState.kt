package com.nelsonxilv.gstoutimetable.presentation.screen

import com.nelsonxilv.gstoutimetable.data.model.Lesson

sealed interface TimetableUiState {

    data class Success(
        val date: String,
        val lessons: List<Lesson>,
        val currentWeekType: Int,
        val selectedSubgroupNumber: Int
    ) : TimetableUiState

    data object Error : TimetableUiState
    data object Loading : TimetableUiState
    data object EmptyTimetable: TimetableUiState
}