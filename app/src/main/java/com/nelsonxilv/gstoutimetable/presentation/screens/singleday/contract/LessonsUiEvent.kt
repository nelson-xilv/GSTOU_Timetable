package com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract

import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiEvent

sealed class LessonsUiEvent : UiEvent {
    data class OnGroupSearch(val groupName: String) : LessonsUiEvent()
    data class OnSubgroupChipClick(val number: Int) : LessonsUiEvent()
}