package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.utils.getCurrentDate
import com.nelsonxilv.gstoutimetable.utils.getCurrentWeekType
import com.nelsonxilv.gstoutimetable.utils.getDayOfWeekNumber
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimetableViewModel : ViewModel() {

    private val repository = TimetableRepository()

    private val _timetableUiState = MutableStateFlow<TimetableUiState>(TimetableUiState.Loading)
    val timetableUiState: StateFlow<TimetableUiState> = _timetableUiState

    init {
        getTodaySchedule("ивт-23-1э")
    }

    fun getTodaySchedule(groupName: String) {
        val correctGroupName = removeAllWhitespace(groupName)
        viewModelScope.launch {
            _timetableUiState.value = TimetableUiState.Loading
            try {
                _timetableUiState.value = TimetableUiState.Success(
                    date = getCurrentDate(),
                    lessons = filterTodaySchedule(repository.getSchedule(correctGroupName)),
                    currentWeekType = getCurrentWeekType()
                )
            } catch (e: Exception) {
                _timetableUiState.value = TimetableUiState.Error
            }
        }
    }

    private fun filterTodaySchedule(listFromRepository: List<Lesson>): List<Lesson> {
        val dayOfWeekNumber = getDayOfWeekNumber()
        val currentWeekType = getCurrentWeekType()

        val lessonList = listFromRepository.filter { lesson ->
            lesson.dayOfWeek == dayOfWeekNumber && (lesson.week == 0 || lesson.week == currentWeekType)
        }.sortedBy { lesson ->
            lesson.period
        }

        return lessonList
    }

    private fun removeAllWhitespace(input: String): String {
        return input.replace(" ", "")
    }
}