package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.components.FilterChips
import com.nelsonxilv.gstoutimetable.presentation.components.LessonItem
import com.nelsonxilv.gstoutimetable.presentation.components.UniversalList
import com.nelsonxilv.gstoutimetable.presentation.screen.TimetableUiState

private const val FirstSubgroup = 1
private const val SecondSubgroup = 2

@Composable
fun ResultContent(
    uiState: TimetableUiState.Success,
    onFilterChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    Column(
        modifier = modifier.padding(contentPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
        ) {
            Column {
                Text(text = uiState.date)

                Text(
                    text = stringResource(R.string.number_week, uiState.currentWeekType),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val subgroups = listOf(FirstSubgroup, SecondSubgroup)

            FilterChips(
                subgroups = subgroups,
                selectedSubgroupNumber = uiState.selectedSubgroupNumber,
                onFilterChipClick = onFilterChipClick
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        UniversalList(
            listElements = uiState.lessons,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            dynamicCorner = true
        ) { lesson, shape ->
            LessonItem(
                lesson = lesson,
                shape = shape,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}
