package com.nelsonxilv.gstoutimetable.presentation.screens.main.contract

import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import com.nelsonxilv.gstoutimetable.domain.entity.Group
import com.nelsonxilv.gstoutimetable.presentation.core.contract.UiState

data class TimetableUiState(
    val currentGroupName: String? = null,
    val savedGroupList: List<Group> = emptyList(),
    val dateInfo: DateInfo = DateInfo(),
) : UiState