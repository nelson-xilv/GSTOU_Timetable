package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.screens.components.ScheduleTable
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

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
            modifier = modifier.fillMaxSize(),
            contextPadding = contentPadding
        )

        is TimetableUiState.Error -> ErrorScreen(modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(
    date: String,
    lessons: List<Lesson>,
    modifier: Modifier = Modifier,
    contextPadding: PaddingValues
) {
    Column(
        modifier = modifier
            .padding(contextPadding)
    ) {
        Text(
            text = date,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_large))
        )

        Spacer(modifier = Modifier.height(8.dp))

        ScheduleTable(
            lessons = lessons,
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading, please wait..."
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
            contentDescription = ""
        )
        Text(
            text = "Loading failed",
            modifier = Modifier.padding(16.dp)
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
        dayOfWeek = 1
    )
    val fakeLesson2 = Lesson(
        name = "Английский язык",
        teacher = "Катровский И.С.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("10:30", "11:50"),
        activityType = "Лек.",
        dayOfWeek = 1
    )
    val fakeLesson3 = Lesson(
        name = "История",
        teacher = "Штефанов А.А.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("13:00", "14:20"),
        activityType = "Пр.",
        dayOfWeek = 1
    )
    val fakeLesson4 = Lesson(
        name = "Программирование",
        teacher = "Эванс Н.Х.",
        auditorium = "ГУК 4-06",
        groups = listOf("ИВТ-23-1э"),
        timeInterval = TimeInterval("14:30", "15:50"),
        activityType = "Пр.",
        dayOfWeek = 1
    )

    val list = listOf(fakeLesson1, fakeLesson2, fakeLesson3, fakeLesson4)

    GSTOUTimetableTheme {
        HomeScreen(
            timetableUiState = TimetableUiState.Success(
                "22 March, 2023",
                list
            )
        )
    }
}