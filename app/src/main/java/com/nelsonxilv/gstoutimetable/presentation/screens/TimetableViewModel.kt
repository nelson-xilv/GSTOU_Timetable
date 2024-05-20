package com.nelsonxilv.gstoutimetable.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimeService
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimetableViewModel : ViewModel() {

    private val repository = TimetableRepository()
    private val timeService = TimeService()

    private val _timetableUiState = MutableStateFlow<TimetableUiState>(TimetableUiState.Loading)
    val timetableUiState: StateFlow<TimetableUiState> = _timetableUiState

    init {
        getTodaySchedule()
    }

    private fun getTodaySchedule() {
        viewModelScope.launch {
            _timetableUiState.value = TimetableUiState.Loading
            try {
                _timetableUiState.value = TimetableUiState.Success(
                    date = timeService.getCurrentDate(),
                    lessons = filterTodaySchedule(repository.getSchedule())
                )
            } catch (e: Exception) {
                _timetableUiState.value = TimetableUiState.Error
            }
        }
    }

    private fun filterTodaySchedule(listFromRepository: List<Lesson>): List<Lesson> {
        val dayOfWeekNumber = timeService.getDayOfWeekNumber()
        val currentWeekType = timeService.getCurrentWeekType()

        val lessonList = listFromRepository.filter { lesson ->
            lesson.dayOfWeek == dayOfWeekNumber && (lesson.week == 0 || lesson.week == currentWeekType)
        }.sortedBy { lesson ->
            lesson.period
        }

        Log.d(TAG, "Current week type: $currentWeekType")
        Log.d(TAG, "Number day of week: $dayOfWeekNumber\nLesson list: $lessonList")
        return lessonList
    }

    companion object {
        private const val TAG = "TimetableViewModel"
    }
}