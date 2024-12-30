package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.nelsonxilv.gstoutimetable.R

private const val FirstSubgroup = 1
private const val SecondSubgroup = 2

@Composable
fun TimetableInfoBar(
    showFilterChips: Boolean,
    date: String,
    weekNumber: Int,
    onFilterChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedSubgroupNumber: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    ) {
        Column {
            Text(text = date)

            Text(
                text = stringResource(R.string.number_week, weekNumber),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        val subgroups = listOf(FirstSubgroup, SecondSubgroup)

        if (showFilterChips) {
            FilterChips(
                subgroups = subgroups,
                selectedSubgroupNumber = selectedSubgroupNumber,
                onFilterChipClick = onFilterChipClick
            )
        }
    }
}