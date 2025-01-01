package com.nelsonxilv.gstoutimetable.presentation.screens.main.contract

import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiEvent

sealed interface TimetableUiEvent : UiEvent {
    class OnGroupSearchClick(val groupName: String) : TimetableUiEvent
    class OnDeleteGroupClick(val groupName: String) : TimetableUiEvent
    data object OnDataUpdate : TimetableUiEvent
}