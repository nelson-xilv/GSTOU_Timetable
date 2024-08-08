package com.nelsonxilv.gstoutimetable.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.utils.getCurrentDate
import com.nelsonxilv.gstoutimetable.utils.getCurrentWeekNumber
import com.nelsonxilv.gstoutimetable.utils.getDayOfWeekNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val repository: TimetableRepository
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Error fetching lessons: $exception")
        _timetableUiState.value = TimetableUiState.Error
    }

    private val _timetableUiState = MutableStateFlow<TimetableUiState>(TimetableUiState.Hello)
    val timetableUiState: StateFlow<TimetableUiState>
        get() = _timetableUiState.asStateFlow()

    private var selectedSubgroupNumber = MutableStateFlow(DEFAULT_SUBGROUP_NUM)

    fun getTodayLessons(groupName: String) {
        val correctGroupName = removeAllWhitespace(groupName)
        val dayOfWeek = getDayOfWeekNumber()
        val currentWeek = getCurrentWeekNumber()

        viewModelScope.launch(coroutineExceptionHandler) {
            _timetableUiState.value = TimetableUiState.Loading
            repository.getLessons(correctGroupName)
                .collect { lessons ->
                    updateState(
                        correctGroupName,
                        lessons.filterTodayLessons(dayOfWeek, currentWeek)
                    )
                    updateSelectedSubgroup(selectedSubgroupNumber.value)
                }
        }
    }

    fun updateSelectedSubgroup(number: Int) {
        selectedSubgroupNumber.value = number
        viewModelScope.launch {
            val currentState = _timetableUiState.value
            if (currentState is TimetableUiState.Success) {
                val filteredLessons = filterBySubgroup(
                    currentLessons = currentState.lessons,
                    selectedSubgroupNumber = number
                )

                updateState(currentState.currentGroup, filteredLessons)
            }
        }
    }

    private fun List<Lesson>.filterTodayLessons(
        dayOfWeek: Int,
        currentWeekNumber: Int
    ): List<Lesson> {
        return this.filter { lesson ->
            lesson.dayOfWeek == dayOfWeek && (lesson.week == currentWeekNumber || lesson.week == 0)
        }.sortedBy { lesson ->
            lesson.period
        }
    }

    private fun filterBySubgroup(
        currentLessons: List<Lesson>,
        selectedSubgroupNumber: Int
    ): List<Lesson> {
        return currentLessons.filter { lesson ->
            lesson.subgroupNumber == 0 || lesson.subgroupNumber == selectedSubgroupNumber
        }
    }

    private fun updateState(groupName: String, filteredLessons: List<Lesson>) {
        if (filteredLessons.isEmpty()) {
            _timetableUiState.value = TimetableUiState.EmptyTimetable
        } else {
            _timetableUiState.value = TimetableUiState.Success(
                date = getCurrentDate(),
                lessons = filteredLessons,
                currentWeekType = getCurrentWeekNumber(),
                selectedSubgroupNumber = selectedSubgroupNumber.value,
                currentGroup = groupName.uppercase(getDefault())
            )
        }
    }

    private fun removeAllWhitespace(input: String): String {
        return input.replace(" ", "")
    }

    companion object {
        private const val DEFAULT_SUBGROUP_NUM = 1
        private const val TAG = "TimetableViewModel"
    }
}