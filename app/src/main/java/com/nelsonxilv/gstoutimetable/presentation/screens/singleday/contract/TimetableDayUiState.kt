package com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract

import androidx.compose.runtime.Immutable
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState

private const val INITIAL_DATE_STRING = "1 September, Sunday"
private const val INITIAL_WEEK = 1
private const val DEFAULT_SUBGROUP_NUM = 1

@Immutable
data class InfoBarState(
    val showFilterChips: Boolean = false,
    val dateInfo: DateInfo = DateInfo(INITIAL_DATE_STRING, INITIAL_WEEK),
    val selectedSubgroupNumber: Int = DEFAULT_SUBGROUP_NUM,
)

@Immutable
data class TimetableDayUiState(
    val lessons: List<Lesson> = listOf(),
    val filteredLessons: List<Lesson> = listOf(),
    val selectedSubgroupNumber: Int = DEFAULT_SUBGROUP_NUM,
    val dateType: DateType = DateType.TODAY,
    val dateInfo: DateInfo = DateInfo(INITIAL_DATE_STRING, INITIAL_WEEK),
    val currentGroup: String = "",
    val isLoading: Boolean = false,
    val isLoadingLessonsError: Boolean = false,
    val errorMessage: String = "",
) : UiState {

    val isEmptyLessonList: Boolean
        get() = lessons.isEmpty() && currentGroup.isNotBlank() && !isLoadingLessonsError

    val isInitialState: Boolean
        get() = lessons.isEmpty() && currentGroup.isBlank()

    val showFilterChips: Boolean
        get() = lessons.isNotEmpty() && lessons.any { it.subgroupNumber != 0 }

    fun getInfoBarState(): InfoBarState {
        return  InfoBarState(
            showFilterChips = this.showFilterChips,
            dateInfo = this.dateInfo,
            selectedSubgroupNumber = this.selectedSubgroupNumber
        )
    }

}