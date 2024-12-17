package com.nelsonxilv.gstoutimetable.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    todayScreenContent: @Composable () -> Unit,
    tomorrowScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TodayScreen,
    ) {

        composable<Screen.TodayScreen> {
            todayScreenContent()
        }

        composable<Screen.TomorrowScreen> {
            tomorrowScreenContent()
        }

    }
}
