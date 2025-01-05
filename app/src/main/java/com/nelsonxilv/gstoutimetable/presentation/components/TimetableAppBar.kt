package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    titleAppBar: String,
    isDataUpdating: Boolean,
    onUpdateButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = titleAppBar,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            IconButton(
                onClick = onUpdateButtonClick,
                enabled = !isDataUpdating
            ) {
                if (isDataUpdating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size = dimensionResource(id = R.dimen.icon_size))
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_update_data),
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullSearchBar(
    savedGroups: List<Group>,
    isSearchVisible: Boolean,
    onQueryChange: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
    onGroupItemClick: (String) -> Unit,
    onClearIconButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    val onActiveChange: (Boolean) -> Unit = { active -> onActiveChanged(active) }
    val colors = SearchBarDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        dividerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    )

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchText,
                onQueryChange = { query -> searchText = query },
                onSearch = { query ->
                    onQueryChange(query)
                    onActiveChanged(false)
                },
                expanded = isSearchVisible,
                onExpandedChange = onActiveChange,
                placeholder = { Text(text = stringResource(R.string.search_by_group)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )
        },
        expanded = isSearchVisible,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        colors = colors
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
                    onClearIconButtonCLick = onClearIconButtonClick,
                    shape = shape,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}