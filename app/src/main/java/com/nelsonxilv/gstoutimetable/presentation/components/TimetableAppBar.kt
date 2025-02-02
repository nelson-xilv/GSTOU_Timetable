package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.nelsonxilv.gstoutimetable.R

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
        modifier = modifier,
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
        }
    )
}