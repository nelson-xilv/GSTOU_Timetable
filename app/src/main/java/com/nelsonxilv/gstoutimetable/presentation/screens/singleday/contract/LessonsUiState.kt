package com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState

data class LessonsUiState(
    val lessons: List<Lesson> = listOf(),
    val selectedSubgroupNumber: Int = DEFAULT_SUBGROUP_NUM,
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
        get() = isEmptyLessonList || lessons.isNotEmpty()

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
    }

}