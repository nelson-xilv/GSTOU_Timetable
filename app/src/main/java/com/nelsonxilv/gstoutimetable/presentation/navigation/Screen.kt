package com.nelsonxilv.gstoutimetable.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object TodayScreen : Screen

    @Serializable
    data object TomorrowScreen : Screen

    @Serializable
    data object WeekScreen : Screen

}