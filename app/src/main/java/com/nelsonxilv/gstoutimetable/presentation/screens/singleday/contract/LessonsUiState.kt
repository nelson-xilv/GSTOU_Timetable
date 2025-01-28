package com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState

data class LessonsUiState(
    val lessons: List<Lesson> = listOf(),
    val filteredLessons: List<Lesson> = listOf(),
    val selectedSubgroupNumber: Int = DEFAULT_SUBGROUP_NUM,
    val dateType: DateType = DateType.TODAY,
    val dateInfo: DateInfo = DateInfo(INITIAL_DATE_STRING, INITIAL_WEEK),
    val currentGroup: String = "",
    val isLoading: Boolean = false,
    val isLoadingLessonsError: Boolean = false,
    val errorMessage: String? = null
) : UiState {

    val isEmptyLessonList: Boolean
        get() = lessons.isEmpty() && currentGroup.isNotBlank() && !isLoadingLessonsError

    val isInitialState: Boolean
        get() = lessons.isEmpty() && currentGroup.isBlank()

    val showFilterChips: Boolean
        get() = lessons.isNotEmpty() && lessons.any { it.subgroupNumber != 0 }

    @DrawableRes
    val loadingLessonsErrorImageId: Int = R.drawable.error_img

    @StringRes
    val loadingLessonsErrorMessageId: Int = R.string.loading_failed

    @DrawableRes
    val emptyLessonListImageId: Int = R.drawable.sleep_img

    @StringRes
    val emptyLessonListMessageId: Int = R.string.you_can_relax

    @DrawableRes
    val greetingImageId: Int = R.drawable.search_groups_img

    @StringRes
    val greetingMessageId: Int = R.string.hello_there

    companion object {
        private const val DEFAULT_SUBGROUP_NUM = 1
        private const val INITIAL_DATE_STRING = "1 September, Sunday"
        private const val INITIAL_WEEK = 1
    }

}