package com.nelsonxilv.gstoutimetable.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nelsonxilv.gstoutimetable.presentation.components.FullSearchBar
import com.nelsonxilv.gstoutimetable.presentation.components.TimetableAppBar
import com.nelsonxilv.gstoutimetable.presentation.screen.HomeScreen
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(timetableViewModel: TimetableViewModel = viewModel()) {

    val timetableUiState by timetableViewModel.timetableUiState.collectAsState()
    val savedGroups by timetableViewModel.savedGroups.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isSearchVisible by remember { mutableStateOf(false) }

    BackHandler(enabled = isSearchVisible) {
        isSearchVisible = false
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TimetableAppBar(
                scrollBehavior = scrollBehavior,
                timetableUiState = timetableUiState
            )

            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FullSearchBar(
                    savedGroups = savedGroups,
                    isSearchVisible = isSearchVisible,
                    onQueryChange = { query -> timetableViewModel.getTodayLessons(query) },
                    onActiveChanged = { isSearchVisible = it },
                    onGroupItemClick = { groupName ->
                        timetableViewModel.getTodayLessons(groupName)
                        isSearchVisible = false
                    },
                    onClearIconButtonCLick = { groupName ->
                        timetableViewModel.deleteGroupAndLessons(groupName)
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
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                timetableUiState = timetableUiState,
                contentPadding = it,
                onFilterChipClick = { subgroupNumber ->
                    timetableViewModel.updateSelectedSubgroup(subgroupNumber)
                },
                onClickCard = { isSearchVisible = true },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}