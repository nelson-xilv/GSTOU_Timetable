package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TimetableViewModel : ViewModel() {
    var timetableUiState: TimetableUiState by mutableStateOf(TimetableUiState.Loading)
        private set

    init {
        getTimetable()
    }

    fun getTimetable() {
        viewModelScope.launch {
            timetableUiState = TimetableUiState.Loading
            timetableUiState = try {
                val listResult = TimetableApiFactory.retrofitService.getSchedule()
                TimetableUiState.Success(
                    "Succes: ${listResult.size} lessons retrieved"
                )
            } catch (e: IOException) {
                TimetableUiState.Error
            } catch (e: HttpException) {
                TimetableUiState.Error
            }
        }
    }
}