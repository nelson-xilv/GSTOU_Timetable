package com.nelsonxilv.gstoutimetable.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.utils.getCurrentDate
import com.nelsonxilv.gstoutimetable.utils.getCurrentWeekType
import com.nelsonxilv.gstoutimetable.utils.getDayOfWeekNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val repository: TimetableRepository
) : ViewModel() {

    private val _timetableUiState = MutableStateFlow<TimetableUiState>(TimetableUiState.Hello)
    val timetableUiState: StateFlow<TimetableUiState> = _timetableUiState

    private val todayLessons = MutableStateFlow<List<Lesson>>(emptyList())
    private val currentGroup = MutableStateFlow("")
    private var selectedSubgroupNumber = MutableStateFlow(DEFAULT_SUBGROUP_NUM)

    fun getTodaySchedule(groupName: String) {
        val correctGroupName = removeAllWhitespace(groupName)
        viewModelScope.launch {
            _timetableUiState.value = TimetableUiState.Loading
            try {
                val allLessons = repository.getSchedule(correctGroupName)
                todayLessons.value = filterTodaySchedule(allLessons)
                currentGroup.value = groupName.uppercase(getDefault())
                updateState(todayLessons.value)
                updateSelectedSubgroup(selectedSubgroupNumber.value)
            } catch (e: Exception) {
                _timetableUiState.value = TimetableUiState.Error
            }
        }
    }

    fun updateSelectedSubgroup(number: Int) {
        selectedSubgroupNumber.value = number
        viewModelScope.launch {
            val currentState = _timetableUiState.value
            if (currentState is TimetableUiState.Success) {
                val filteredLessons = filterBySubgroup(
                    currentLessons = todayLessons.value,
                    selectedSubgroupNumber = number
                )

                updateState(filteredLessons)
            }
        }
    }

    private fun filterTodaySchedule(listFromRepository: List<Lesson>): List<Lesson> {
        val dayOfWeekNumber = getDayOfWeekNumber()
        val currentWeekType = getCurrentWeekType()

        val lessonList = listFromRepository.filter { lesson ->
            lesson.dayOfWeek == dayOfWeekNumber &&
                    (lesson.week == 0 || lesson.week == currentWeekType)
        }.sortedBy { lesson ->
            lesson.period
        }

        return lessonList
    }

    private fun filterBySubgroup(
        currentLessons: List<Lesson>,
        selectedSubgroupNumber: Int
    ): List<Lesson> {
        return currentLessons.filter { lesson ->
            lesson.subgroupNumber == 0 || lesson.subgroupNumber == selectedSubgroupNumber
        }
    }

    private fun updateState(filteredLessons: List<Lesson>) {
        if (filteredLessons.isEmpty()) {
            _timetableUiState.value = TimetableUiState.EmptyTimetable
        } else {
            _timetableUiState.value = TimetableUiState.Success(
                date = getCurrentDate(),
                lessons = filteredLessons,
                currentWeekType = getCurrentWeekType(),
                selectedSubgroupNumber = selectedSubgroupNumber.value,
                currentGroup = currentGroup.value
            )
        }
    }

    private fun removeAllWhitespace(input: String): String {
        return input.replace(" ", "")
    }

    companion object {
        private const val DEFAULT_SUBGROUP_NUM = 1
    }
}