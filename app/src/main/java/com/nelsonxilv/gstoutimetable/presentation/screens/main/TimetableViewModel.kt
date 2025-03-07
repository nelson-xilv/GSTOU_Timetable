package com.nelsonxilv.gstoutimetable.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.nelsonxilv.gstoutimetable.di.DefaultCoroutineExceptionHandler
import com.nelsonxilv.gstoutimetable.domain.usecase.DeleteGroupAndLessonsUseCase
import com.nelsonxilv.gstoutimetable.domain.usecase.GetGroupListUseCase
import com.nelsonxilv.gstoutimetable.domain.usecase.UpdateDataUseCase
import com.nelsonxilv.gstoutimetable.presentation.core.viewmodel.BaseViewModel
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent.OnDataUpdate
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
    private val deleteGroupAndLessonsUseCase: DeleteGroupAndLessonsUseCase,
    private val updateDataUseCase: UpdateDataUseCase,
    @DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<TimetableUiState, TimetableUiEvent>(TimetableUiState()) {

    init {
        getGroupList()
    }

    override fun handleEvent(event: TimetableUiEvent) {
        when (event) {
            is OnGroupSearchClick -> setGroupNameInTopBar(event.groupName)
            is OnDeleteGroupClick -> deleteGroupAndLessons(event.groupName)
            is OnDataUpdate -> updateData()
        }
    }

    private fun setGroupNameInTopBar(groupName: String) {
        if (groupName.isNotEmpty()) {
            val correctGroupName = groupName.formatGroupName()
            setState(currentState.copy(currentGroupName = correctGroupName))
        } else {
            setState(currentState.copy(currentGroupName = null))
        }
    }

    private fun deleteGroupAndLessons(groupName: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            deleteGroupAndLessonsUseCase(groupName)
            if (currentState.currentGroupName == groupName)
                setState(currentState.copy(currentGroupName = null))
        }
    }

    private fun updateData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            setState(currentState.copy(isDataUpdating = true))
            updateDataUseCase()
            setState(currentState.copy(isDataUpdating = false))
        }
    }

    private fun getGroupList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            getGroupListUseCase().collect { groups ->
                setState(currentState.copy(savedGroupList = groups))
            }
        }
    }

}