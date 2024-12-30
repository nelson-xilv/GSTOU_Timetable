package com.nelsonxilv.gstoutimetable.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.di.DefaultCoroutineExceptionHandler
import com.nelsonxilv.gstoutimetable.domain.usecase.DeleteGroupAndLessonsUseCase
import com.nelsonxilv.gstoutimetable.domain.usecase.GetDateUseCase
import com.nelsonxilv.gstoutimetable.domain.usecase.GetGroupListUseCase
import com.nelsonxilv.gstoutimetable.presentation.core.viewmodel.BaseViewModel
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent.OnDeleteGroupClick
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent.OnGroupSearchClick
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiState
import com.nelsonxilv.gstoutimetable.utils.formatGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getGroupListUseCase: GetGroupListUseCase,
    private val getDateUseCase: GetDateUseCase,
    private val deleteGroupAndLessonsUseCase: DeleteGroupAndLessonsUseCase,
    @DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<TimetableUiState, TimetableUiEvent>(TimetableUiState()) {

    init {
        loadInitData()
    }

    override fun handleEvent(event: TimetableUiEvent) {
        when (event) {
            is OnGroupSearchClick -> setGroupNameInTopBar(event.groupName)
            is OnDeleteGroupClick -> deleteGroupAndLessons(event.groupName)
        }
    }

    private fun setGroupNameInTopBar(groupName: String) {
        if (groupName.isNotEmpty()) {
            val correctGroupName = formatGroupName(groupName)
            setState(currentState.copy(currentGroupName = correctGroupName))
        } else {
            setState(currentState.copy(currentGroupName = null))
        }
    }

    private fun deleteGroupAndLessons(groupName: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            deleteGroupAndLessonsUseCase(groupName)
        }
    }

    private fun loadInitData() {
        getInfo()
        getGroupList()
    }

    private fun getInfo() {
        val dateInfo = getDateUseCase()
        setState(currentState.copy(dateInfo = dateInfo))
    }

    private fun getGroupList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            getGroupListUseCase().collect { groups ->
                setState(currentState.copy(savedGroupList = groups))
            }
        }
    }

}