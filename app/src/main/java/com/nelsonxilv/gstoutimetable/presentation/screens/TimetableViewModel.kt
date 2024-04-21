package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableApi
import kotlinx.coroutines.launch

class TimetableViewModel : ViewModel() {
    var timetableUiState: String by mutableStateOf("")
        private set

    init {
        getTimetable()
    }

    fun getTimetable() {
        viewModelScope.launch {
            val listResult = TimetableApi.retrofitService.getSchedule()
            timetableUiState = listResult
        }
    }
}