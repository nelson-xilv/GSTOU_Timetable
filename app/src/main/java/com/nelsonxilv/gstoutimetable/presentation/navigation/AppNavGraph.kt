package com.nelsonxilv.gstoutimetable.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

private const val AnimationDuration = 300

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    todayScreenContent: @Composable () -> Unit,
    tomorrowScreenContent: @Composable () -> Unit,
    weekScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TodayScreen,
    ) {

        composable<Screen.TodayScreen>(
            enterTransition = ::slideInToRight,
            exitTransition = ::slideOutToLeft,
        ) {
            todayScreenContent()
        }

        composable<Screen.TomorrowScreen>(
            enterTransition = { getEnterTransition(this) },
            exitTransition = { getExitTransition(this) },
        ) {
            tomorrowScreenContent()
        }

        composable<Screen.WeekScreen>(
            enterTransition = ::slideInToLeft,
            exitTransition = ::slideOutToRight,
        ) {
            weekScreenContent()
        }

    }
}

private fun getEnterTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.initialState.destination.let {
        if (it.hasRoute(Screen.TodayScreen::class)) {
            slideInToLeft(scope)
        } else {
            slideInToRight(scope)
        }
    }

private fun getExitTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.targetState.destination.let {
        if (it.hasRoute(Screen.TodayScreen::class)) {
            slideOutToRight(scope)
        } else {
            slideOutToLeft(scope)
        }
    }

private fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(AnimationDuration)
    )

private fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(AnimationDuration)
    )

private fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(AnimationDuration)
    )

private fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>) =
    scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(AnimationDuration)
    )
