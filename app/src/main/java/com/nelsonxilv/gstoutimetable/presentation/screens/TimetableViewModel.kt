package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.TimetableRepository
import com.nelsonxilv.gstoutimetable.data.entity.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TimetableViewModel : ViewModel() {

    private val repository = TimetableRepository()

    private val _schedule: MutableStateFlow<List<Lesson>> = MutableStateFlow(emptyList())
    val schedule: StateFlow<List<Lesson>>
        get() = _schedule.asStateFlow()

    var timetableUiState: TimetableUiState by mutableStateOf(TimetableUiState.Loading)
        private set

    init {
        getTimetable()
    }

    private fun getTimetable() {
        viewModelScope.launch {
            timetableUiState = TimetableUiState.Loading
            timetableUiState = try {
                val lessons = repository.getSchedule()
                _schedule.value = lessons
                TimetableUiState.Success(
                    "Succes: ${schedule.value.size} lessons retrieved"
                )
            } catch (e: IOException) {
                TimetableUiState.Error
            } catch (e: HttpException) {
                TimetableUiState.Error
            }
        }
    }
}