package com.nelsonxilv.gstoutimetable.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.presentation.components.GroupItem
import com.nelsonxilv.gstoutimetable.presentation.components.UniversalList
import com.nelsonxilv.gstoutimetable.presentation.screen.HomeScreen
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableApp(timetableViewModel: TimetableViewModel = viewModel()) {

    val timetableUiState by timetableViewModel.timetableUiState.collectAsState()
    val savedGroups by timetableViewModel.savedGroups.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isSearchVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TimetableAppBar(
                scrollBehavior = scrollBehavior,
                onSearchGroupsIconClick = { isSearchVisible = !isSearchVisible },
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
    savedGroups: List<Group>,
    isSearchVisible: Boolean,
    onQueryChange: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
    onGroupItemClick: (String) -> Unit,
    onClearIconButtonCLick: (String) -> Unit,
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
        modifier = modifier.fillMaxWidth(),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            dividerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        if (savedGroups.isNotEmpty()) {

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            UniversalList(
                listElements = savedGroups,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large)),
            ) { group, shape ->
                GroupItem(
                    group = group,
                    onGroupItemClick = onGroupItemClick,
                    onClearIconButtonCLick = onClearIconButtonCLick,
                    shape = shape,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchGroupsIconClick: () -> Unit,
    timetableUiState: TimetableUiState,
    modifier: Modifier = Modifier
) {

    val topAppBarText = if (timetableUiState is TimetableUiState.Success) {
        timetableUiState.currentGroup
    } else {
        stringResource(id = R.string.app_name)
    }

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = topAppBarText,
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