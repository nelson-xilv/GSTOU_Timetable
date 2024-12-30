package com.nelsonxilv.gstoutimetable.presentation.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.presentation.components.FullSearchBar
import com.nelsonxilv.gstoutimetable.presentation.components.TimetableAppBar
import com.nelsonxilv.gstoutimetable.presentation.components.TimetableNavBarItem
import com.nelsonxilv.gstoutimetable.presentation.components.TimetableNavigationBar
import com.nelsonxilv.gstoutimetable.presentation.navigation.AppNavGraph
import com.nelsonxilv.gstoutimetable.presentation.navigation.NavigationItem
import com.nelsonxilv.gstoutimetable.presentation.navigation.isCurrentScreen
import com.nelsonxilv.gstoutimetable.presentation.navigation.rememberNavigationState
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.main.contract.TimetableUiState
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.TimetableOfDayScreen
import com.nelsonxilv.gstoutimetable.presentation.screens.week.WeekScreen

@Composable
fun TimetableApp() {
    val viewModel = hiltViewModel<TimetableViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    TimetableContent(
        state = uiState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableContent(
    state: TimetableUiState = TimetableUiState(),
    onEvent: (TimetableUiEvent) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var searchGroupName by rememberSaveable { mutableStateOf("") }
    var isSearchVisible by rememberSaveable { mutableStateOf(false) }
    val navigationState = rememberNavigationState()

    BackHandler(enabled = isSearchVisible) {
        isSearchVisible = false
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TimetableAppBar(
                scrollBehavior = scrollBehavior,
                titleAppBar = state.currentGroupName ?: stringResource(id = R.string.app_name)
            )

            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FullSearchBar(
                    savedGroups = state.savedGroupList,
                    isSearchVisible = isSearchVisible,
                    onQueryChange = { groupName ->
                        searchGroupName = groupName
                        onEvent(TimetableUiEvent.OnGroupSearchClick(groupName))
                    },
                    onActiveChanged = { isSearchVisible = it },
                    onGroupItemClick = { groupName ->
                        searchGroupName = groupName
                        isSearchVisible = false
                        onEvent(TimetableUiEvent.OnGroupSearchClick(groupName))
                    },
                    onClearIconButtonClick = { groupName ->
                        onEvent(TimetableUiEvent.OnDeleteGroupClick(groupName))
                    }
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = { isSearchVisible = true },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isSearchVisible && state.currentGroupName != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val items = listOf(
                    NavigationItem.Today,
                    NavigationItem.Tomorrow,
                    NavigationItem.Week
                )
                val navBackStackEntry by navigationState
                    .navHostController
                    .currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val selectedItem = rememberSaveable(currentDestination) {
                    derivedStateOf {
                        items.indexOfFirst { screen ->
                            currentDestination?.isCurrentScreen(screen) == true
                        }.coerceAtLeast(0)
                    }.value
                }

                TimetableNavigationBar(
                    itemsListSize = items.size,
                    selectedItemIndex = selectedItem
                ) {
                    items.forEach { screen ->
                        val isSelected = currentDestination?.isCurrentScreen(screen) == true

                        TimetableNavBarItem(
                            text = stringResource(screen.titleResId),
                            selected = isSelected,
                            onClick = { navigationState.navigateTo(screen) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavGraph(
                navHostController = navigationState.navHostController,
                todayScreenContent = {
                    TimetableOfDayScreen(
                        searchGroupName = searchGroupName,
                        dateType = DateType.TODAY,
                        contentPadding = innerPadding,
                        onCardClick = { isSearchVisible = true },
                    )
                },
                tomorrowScreenContent = {
                    TimetableOfDayScreen(
                        searchGroupName = searchGroupName,
                        dateType = DateType.TOMORROW,
                        contentPadding = innerPadding,
                        onCardClick = { isSearchVisible = true },
                    )
                },
                weekScreenContent = { WeekScreen(contentPadding = innerPadding) }
            )
        }
    }
}