package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.screens.components.ScheduleTable
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

private const val FirstSubgroup = 1
private const val SecondSubgroup = 2

@Composable
fun HomeScreen(
    timetableUiState: TimetableUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (timetableUiState) {
        is TimetableUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is TimetableUiState.Success -> ResultScreen(
            date = timetableUiState.date,
            lessons = timetableUiState.lessons,
            currentWeekType = timetableUiState.currentWeekType,
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )

        is TimetableUiState.Error -> ErrorScreen(modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(
    date: String,
    lessons: List<Lesson>,
    currentWeekType: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    var selectedSubgroupNumber by remember { mutableIntStateOf(FirstSubgroup) }

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
                Text(text = date)

                Text(
                    text = stringResource(R.string.number_week, currentWeekType),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val subgroups = listOf(FirstSubgroup, SecondSubgroup)

            Row {
                subgroups.forEach { number ->
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                    FilterChip(
                        selected = number == selectedSubgroupNumber,
                        onClick = { selectedSubgroupNumber = number },
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

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        val filteredLessons = lessons.filter { lesson ->
            lesson.subgroupNumber == 0 || lesson.subgroupNumber == selectedSubgroupNumber
        }

        ScheduleTable(
            lessons = filteredLessons,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

        Text(
            text = stringResource(R.string.loading_please_wait)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_img),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
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
        subgroupNumber = 0
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
        subgroupNumber = 0
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
        subgroupNumber = 0
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
        subgroupNumber = 0
    )

    val list = listOf(fakeLesson1, fakeLesson2, fakeLesson3, fakeLesson4)

    GSTOUTimetableTheme {
        HomeScreen(
            timetableUiState = TimetableUiState.Success(
                "22 March, 2023",
                list,
                2
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}