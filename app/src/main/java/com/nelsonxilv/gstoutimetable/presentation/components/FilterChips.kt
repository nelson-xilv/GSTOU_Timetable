package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun FilterChips(
    subgroups: List<Int>,
    selectedSubgroupNumber: Int,
    onFilterChipClick: (number: Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement
            .spacedBy(dimensionResource(id = R.dimen.padding_small_medium))
    ) {
        subgroups.forEach { number ->
            FilterChip(
                selected = number == selectedSubgroupNumber,
                onClick = { onFilterChipClick(number) },
                label = { Text(stringResource(R.string.subgroup_number, number)) },
                leadingIcon = {
                    if (number == selectedSubgroupNumber) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_group),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun FilterChipsPreview() {
    GSTOUTimetableTheme {
        Surface {
            FilterChips(
                subgroups = listOf(1, 2),
                selectedSubgroupNumber = 1,
                onFilterChipClick = {}
            )
        }
    }
}