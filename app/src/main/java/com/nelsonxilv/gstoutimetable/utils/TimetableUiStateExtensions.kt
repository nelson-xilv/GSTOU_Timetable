package com.nelsonxilv.gstoutimetable.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState.EmptyTimetable
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState.Error
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState.Greeting

@get:DrawableRes
val TimetableUiState.iconResId: Int
    get() = when (this) {
        is Error -> R.drawable.error_img
        is EmptyTimetable -> R.drawable.sleep_img
        is Greeting -> R.drawable.search_groups_img
        else -> 0
    }

@get:StringRes
val TimetableUiState.textResId: Int
    get() = when (this) {
        is Error -> R.string.loading_failed
        is EmptyTimetable -> R.string.you_can_relax
        is Greeting -> R.string.hello_there
        else -> 0
    }