package com.nelsonxilv.gstoutimetable.presentation.screens.week

import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.di.DefaultCoroutineExceptionHandler
import com.nelsonxilv.gstoutimetable.domain.entity.Day
import com.nelsonxilv.gstoutimetable.domain.usecase.GetLessonListForWeekUseCase
import com.nelsonxilv.gstoutimetable.presentation.core.viewmodel.BaseViewModel
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiEvent.OnGroupSearch
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiEvent.OnSubgroupChipClick
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiState
import com.nelsonxilv.gstoutimetable.utils.filterLessonsBySubgroup
import com.nelsonxilv.gstoutimetable.utils.formatGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor(
    private val getLessonsListForWeekUseCase: GetLessonListForWeekUseCase,
    @DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler
) : BaseViewModel<WeekUiState, WeekUiEvent>(WeekUiState()) {

    override fun handleEvent(event: WeekUiEvent) {
        when (event) {
            is OnGroupSearch -> getAllLessonsForWeek(event.groupName)
            is OnSubgroupChipClick -> updateSelectedSubgroup(event.number)
        }
    }

    private fun getAllLessonsForWeek(groupName: String) {
        val correctGroupName = groupName.formatGroupName()
        loadLessonsForGroup(correctGroupName)
    }

    private fun updateSelectedSubgroup(number: Int) {
        if (number != currentState.selectedSubgroupNumber) {

            val daysWithFilteredLessons =
                filteredLessonsForDays(currentState.days, number)

            setState(
                currentState.copy(
                    selectedSubgroupNumber = number,
                    daysWithFilteredLessons = daysWithFilteredLessons
                )
            )

        }
    }

    private fun loadLessonsForGroup(groupName: String) {

        setState(
            currentState.copy(
                isLoading = true,
                errorMessage = "",
                isLoadingLessonsError = false
            )
        )

        viewModelScope.launch(coroutineExceptionHandler) {
            getLessonsListForWeekUseCase(groupName)
                .catch { exception ->
                    setState(
                        currentState.copy(
                            isLoading = false,
                            isLoadingLessonsError = true,
                            errorMessage = exception.message ?: "Unknown error"
                        )
                    )
                }.collect { days ->
                    val daysWithFilteredLessons =
                        filteredLessonsForDays(days, currentState.selectedSubgroupNumber)

                    setState(
                        currentState.copy(
                            days = days,
                            daysWithFilteredLessons = daysWithFilteredLessons,
                            isLoading = false
                        )
                    )
                }
        }
    }

    fun filteredLessonsForDays(
        days: List<Day>,
        subgroupNumber: Int
    ) = days.map { day ->
        val filteredLessons = day.lessons.filterLessonsBySubgroup(subgroupNumber)
        day.copy(lessons = filteredLessons)
    }

}