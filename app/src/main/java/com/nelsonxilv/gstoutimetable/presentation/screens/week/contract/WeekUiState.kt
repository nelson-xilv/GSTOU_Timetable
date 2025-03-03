package com.nelsonxilv.gstoutimetable.presentation.screens.week.contract

import com.nelsonxilv.gstoutimetable.domain.entity.Day
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState

data class WeekUiState(
    val days: List<Day> = listOf(),
    val daysWithFilteredLessons: List<Day> = listOf(),
    val selectedSubgroupNumber: Int = DEFAULT_SUBGROUP_NUM,
    val isLoading: Boolean = false,
    val isLoadingLessonsError: Boolean = false,
    val errorMessage: String = "",
) : UiState {

    val isEmptyLessonList: Boolean
        get() = days.all { it.lessons.isEmpty() } && !isLoadingLessonsError

    companion object {
        private const val DEFAULT_SUBGROUP_NUM = 1
    }

}