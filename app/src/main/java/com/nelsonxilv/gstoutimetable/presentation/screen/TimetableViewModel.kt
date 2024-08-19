package com.nelsonxilv.gstoutimetable.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.utils.getCurrentDate
import com.nelsonxilv.gstoutimetable.utils.getCurrentWeekNumber
import com.nelsonxilv.gstoutimetable.utils.getDayOfWeekNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    private val _timetableUiState = MutableStateFlow<TimetableUiState>(TimetableUiState.Greeting)
    val timetableUiState: StateFlow<TimetableUiState>
        get() = _timetableUiState.asStateFlow()

    private val _savedGroups = MutableStateFlow<List<Group>>(emptyList())
    val savedGroups: StateFlow<List<Group>>
        get() = _savedGroups.asStateFlow()

    private var selectedSubgroupNumber = MutableStateFlow(DEFAULT_SUBGROUP_NUM)
    private val currentWeekNumber = getCurrentWeekNumber()

    init {
        loadInitData()
    }

    fun getTodayLessons(groupName: String) {
        val correctGroupName = correctingNameOfGroup(groupName)
        val dayOfWeek = getDayOfWeekNumber()

        viewModelScope.launch(coroutineExceptionHandler) {
            _timetableUiState.value = TimetableUiState.Loading
            repository.getLessons(correctGroupName)
                .flowOn(Dispatchers.IO)
                .map { lessons ->
                    val filteredLessons = lessons.filterLessons(
                        selectedSubgroupNumber.value,
                        dayOfWeek,
                        currentWeekNumber
                    )
                    updateUiState(correctGroupName, filteredLessons)
                }
                .collect { uiState ->
                    _timetableUiState.value = uiState
                }
        }
    }

    fun updateSelectedSubgroup(number: Int) {
        selectedSubgroupNumber.value = number
        viewModelScope.launch {
            val currentState = _timetableUiState.value
            if (currentState is TimetableUiState.Success) {
                val filteredLessons = currentState.lessons.filterLessons(number)
                val uiState = updateUiState(
                    currentState.currentGroup,
                    filteredLessons
                )
                _timetableUiState.value = uiState
            }
        }
    }

    fun deleteGroupAndLessons(groupName: String) {
        viewModelScope.launch {
            repository.deleteGroupAndLessons(groupName)
        }
    }

    private fun loadInitData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getAllGroups().collect { groups ->
                _savedGroups.value = groups.reversed()
            }
        }
    }

    private fun List<Lesson>.filterLessons(
        selectedSubgroupNumber: Int,
        dayOfWeek: Int? = null,
        currentWeekNumber: Int? = null
    ): List<Lesson> {
        return this.filter { lesson ->
            (dayOfWeek == null || lesson.dayOfWeek == dayOfWeek) &&
                    (currentWeekNumber == null || lesson.week == currentWeekNumber
                            || lesson.week == NO_WEEK) &&
                    (lesson.subgroupNumber == NO_SUBGROUP
                            || lesson.subgroupNumber == selectedSubgroupNumber)
        }.sortedBy { lesson ->
            lesson.period
        }
    }

    private fun updateUiState(
        groupName: String,
        filteredLessons: List<Lesson>
    ): TimetableUiState {
        return if (filteredLessons.isEmpty()) {
            TimetableUiState.EmptyTimetable
        } else {
            TimetableUiState.Success(
                date = getCurrentDate(),
                lessons = filteredLessons,
                currentWeekType = currentWeekNumber,
                selectedSubgroupNumber = selectedSubgroupNumber.value,
                currentGroup = groupName
            )
        }
    }

    private fun correctingNameOfGroup(groupName: String): String {
        return groupName.replace(" ", "").uppercase(getDefault())
    }

    companion object {
        private const val DEFAULT_SUBGROUP_NUM = 1
        private const val NO_SUBGROUP = 0
        private const val NO_WEEK = 0
        private const val TAG = "TimetableViewModel"
    }
}