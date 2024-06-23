package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nelsonxilv.gstoutimetable.R

@Composable
fun FilterChips(
    subgroups: List<Int>,
    selectedSubgroupNumber: Int,
    onFilterChipClick: (number: Int) -> Unit
) {
    Row {
        subgroups.forEach { number ->
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            FilterChip(
                selected = number == selectedSubgroupNumber,
                onClick = { onFilterChipClick(number) },
                label = {
                    Text(stringResource(R.string.subgroup_number, number))
                },
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