package com.nelsonxilv.gstoutimetable.presentation.screens.week.contract

import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiEvent

sealed interface WeekUiEvent : UiEvent {
    data class OnGroupSearch(val groupName: String) : WeekUiEvent
    data class OnSubgroupChipClick(val number: Int) : WeekUiEvent
}