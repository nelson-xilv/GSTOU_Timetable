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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.components.FilterChips
import com.nelsonxilv.gstoutimetable.presentation.components.ScheduleTable
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

        ScheduleTable(
            lessons = uiState.lessons,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    val fakeLesson1 = Lesson(
        name = "Психология",
        teacher = "Солдатский Л.В.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э", "ИCТ-23-2э"),
        timeInterval = TimeInterval("9:00", "10:20"),
        activityType = "Лек.",
        period = 1,
        dayOfWeek = 1,
        week = 0,
        subgroupNumber = 1
    )
    val fakeLesson2 = Lesson(
        name = "Английский язык",
        teacher = "Катровский И.С.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("10:30", "11:50"),
        activityType = "Лек.",
        period = 2,
        dayOfWeek = 1,
        week = 0,
        subgroupNumber = 1
    )
    val fakeLesson3 = Lesson(
        name = "История",
        teacher = "Штефанов А.А.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("13:00", "14:20"),
        activityType = "Пр.",
        period = 3,
        dayOfWeek = 1,
        week = 0,
        subgroupNumber = 2
    )
    val fakeLesson4 = Lesson(
        name = "Программирование",
        teacher = "Эванс Н.Х.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("14:30", "15:50"),
        activityType = "Пр.",
        period = 4,
        dayOfWeek = 1,
        week = 0,
        subgroupNumber = 2
    )

    val list = listOf(fakeLesson1, fakeLesson2, fakeLesson3, fakeLesson4)

    ResultContent(
        uiState = TimetableUiState.Success(
            "22 March, 2023",
            list,
            2,
            1,
            "ИВТ-23М"
        ),
        onFilterChipClick = {  },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp)
    )
}
