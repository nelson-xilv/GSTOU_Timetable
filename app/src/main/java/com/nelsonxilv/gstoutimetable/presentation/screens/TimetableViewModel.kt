package com.nelsonxilv.gstoutimetable.presentation.screens

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class TimetableViewModel : ViewModel() {

    private val repository = TimetableRepository()

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
                    date = getCurrentDate(),
                    lessons = repository.getTodaySchedule()
                )
            } catch (e: Exception) {
                _timetableUiState.value = TimetableUiState.Error
            }
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM, EEEE", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}