package com.nelsonxilv.gstoutimetable.presentation.screens.singleday

import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.di.DefaultCoroutineExceptionHandler
import com.nelsonxilv.gstoutimetable.domain.usecase.GetTodayLessonListUseCase
import com.nelsonxilv.gstoutimetable.presentation.core.viewmodel.BaseViewModel
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiEvent.OnGroupSearch
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiEvent.OnSubgroupChipClick
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiState
import com.nelsonxilv.gstoutimetable.utils.formatGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableOfDayViewModel @Inject constructor(
    private val getTodayLessonListUseCase: GetTodayLessonListUseCase,
    @DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<LessonsUiState, LessonsUiEvent>(LessonsUiState()) {

    override fun handleEvent(event: LessonsUiEvent) {
        when (event) {
            is OnGroupSearch -> getTodayLessons(event.groupName)
            is OnSubgroupChipClick -> updateSelectedSubgroup(event.number)
        }
    }

    private fun getTodayLessons(groupName: String) {
        if (groupName.isNotEmpty()) {
            val correctGroupName = formatGroupName(groupName)
            setState(currentState.copy(currentGroup = correctGroupName))
            loadLessonsForGroup()
        }
    }

    private fun updateSelectedSubgroup(number: Int) {
        if (number != currentState.selectedSubgroupNumber) {
            setState(currentState.copy(selectedSubgroupNumber = number))
            loadLessonsForGroup()
        }
    }

    private fun loadLessonsForGroup() {

        setState(
            currentState.copy(
                isLoading = true,
                errorMessage = null,
                isLoadingLessonsError = false
            )
        )

        viewModelScope.launch(coroutineExceptionHandler) {
            getTodayLessonListUseCase(
                currentState.currentGroup,
                currentState.selectedSubgroupNumber
            ).catch { exception ->
                setState(
                    currentState.copy(
                        isLoading = false,
                        isLoadingLessonsError = true,
                        errorMessage = exception.message ?: "Unknown error"
                    )
                )
            }.collect { lessons ->
                setState(currentState.copy(lessons = lessons, isLoading = false))
            }
        }

    }
}