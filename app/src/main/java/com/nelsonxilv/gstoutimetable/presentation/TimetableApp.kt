package com.nelsonxilv.gstoutimetable.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.screen.HomeScreen
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableApp() {
    val timetableViewModel: TimetableViewModel = viewModel()
    val timetableUiState by timetableViewModel.timetableUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isSearchVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TimetableAppBar(
                scrollBehavior = scrollBehavior,
                onSearchGroupsIconClick = { isSearchVisible = !isSearchVisible }
            )

            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FullSearchBar(
                    onQueryChange = { query -> timetableViewModel.getTodaySchedule(query) },
                    isSearchVisible = isSearchVisible,
                    onActiveChanged = { isSearchVisible = it }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                HomeScreen(
                    timetableUiState = timetableUiState,
                    contentPadding = it,
                    onFilterChipClick = { subgroupNumber ->
                        timetableViewModel.updateSelectedSubgroup(subgroupNumber)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullSearchBar(
    onQueryChange: (String) -> Unit,
    isSearchVisible: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    SearchBar(
        query = searchText,
        onQueryChange = { query -> searchText = query },
        onSearch = { query ->
            onQueryChange(query)
            onActiveChanged(false)
        },
        active = isSearchVisible,
        onActiveChange = { active -> onActiveChanged(active) },
        placeholder = { Text(text = stringResource(R.string.search_by_group)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        modifier = modifier.fillMaxWidth()
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchGroupsIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            IconButton(onClick = onSearchGroupsIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_groups),
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun TimeTableAppBarPreview() {
    TimetableApp()
}