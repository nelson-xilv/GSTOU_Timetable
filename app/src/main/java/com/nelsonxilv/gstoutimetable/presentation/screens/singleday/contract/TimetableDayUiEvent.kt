package com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract

import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiEvent

sealed interface TimetableDayUiEvent : UiEvent {
    data class OnGroupSearch(val groupName: String) : TimetableDayUiEvent
    data class OnSubgroupChipClick(val number: Int) : TimetableDayUiEvent
}