package com.nelsonxilv.gstoutimetable.presentation.screens.singleday

import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.di.DefaultCoroutineExceptionHandler
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.domain.usecase.GetDateUseCase
import com.nelsonxilv.gstoutimetable.domain.usecase.GetLessonListForDayUseCase
import com.nelsonxilv.gstoutimetable.presentation.core.viewmodel.BaseViewModel
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiEvent.OnGroupSearch
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiEvent.OnSubgroupChipClick
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiState
import com.nelsonxilv.gstoutimetable.utils.filterLessonsBySubgroup
import com.nelsonxilv.gstoutimetable.utils.formatGroupName
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TimetableDayViewModel.Factory::class)
class TimetableDayViewModel @AssistedInject constructor(
    private val getLessonListForDayUseCase: GetLessonListForDayUseCase,
    private val getDateUseCase: GetDateUseCase,
    @Assisted private val dateType: DateType,
    @DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<TimetableDayUiState, TimetableDayUiEvent>(TimetableDayUiState()) {

    @AssistedFactory
    interface Factory {
        fun create(dateType: DateType): TimetableDayViewModel
    }

    init {
        getDateInfo()
    }

    override fun handleEvent(event: TimetableDayUiEvent) {
        when (event) {
            is OnGroupSearch -> getTodayLessons(event.groupName)
            is OnSubgroupChipClick -> updateSelectedSubgroup(event.number)
        }
    }

    private fun getTodayLessons(groupName: String) {
        if (groupName.isNotEmpty()) {
            val correctGroupName = groupName.formatGroupName()
            setState(currentState.copy(currentGroup = correctGroupName, dateType = dateType))
            loadLessonsForGroup()
        } else {
            setState(currentState.copy(currentGroup = groupName))
            getDateInfo()
        }
    }

    private fun updateSelectedSubgroup(number: Int) {
        if (number != currentState.selectedSubgroupNumber) {
            val filteredLessons = currentState.lessons.filterLessonsBySubgroup(number)
            setState(
                currentState.copy(
                    selectedSubgroupNumber = number,
                    filteredLessons = filteredLessons
                )
            )
        }
    }

    private fun getDateInfo() {
        val dateInfo = getDateUseCase(dateType)
        setState(currentState.copy(dateInfo = dateInfo))
    }

    private fun loadLessonsForGroup() {

        setState(
            currentState.copy(
                isLoading = true,
                errorMessage = "",
                isLoadingLessonsError = false
            )
        )

        viewModelScope.launch(coroutineExceptionHandler) {
            getLessonListForDayUseCase(
                currentState.currentGroup,
                currentState.dateType,
            ).catch { exception ->
                setState(
                    currentState.copy(
                        isLoading = false,
                        isLoadingLessonsError = true,
                        errorMessage = exception.message ?: "Unknown error"
                    )
                )
            }.collect { lessons ->
                val filteredLessons =
                    lessons.filterLessonsBySubgroup(currentState.selectedSubgroupNumber)

                setState(
                    currentState.copy(
                        lessons = lessons,
                        filteredLessons = filteredLessons,
                        isLoading = false
                    )
                )
            }
        }

    }

}