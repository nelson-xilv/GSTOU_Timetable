package com.nelsonxilv.gstoutimetable.presentation.screens.week

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.Day
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.domain.entity.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.components.DayItem
import com.nelsonxilv.gstoutimetable.presentation.components.content.CenteredContentBox
import com.nelsonxilv.gstoutimetable.presentation.components.content.ContentContainerOption
import com.nelsonxilv.gstoutimetable.presentation.components.content.LoadingContent
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.week.contract.WeekUiState
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun WeekScreen(
    searchGroupName: String?,
    contentPadding: PaddingValues,
    onCardClick: () -> Unit,
) {
    val viewModel = hiltViewModel<WeekViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    WeekContent(
        uiState = uiState,
        searchGroupName = searchGroupName ?: "",
        contentPadding = contentPadding,
        onEvent = viewModel::handleEvent,
        onCardClick = onCardClick,
        onCopied = {
            Toast.makeText(
                context,
                R.string.text_copied,
                Toast.LENGTH_SHORT
            ).show()
        }
    )

}

@Composable
fun WeekContent(
    uiState: WeekUiState = WeekUiState(),
    searchGroupName: String = "",
    contentPadding: PaddingValues = PaddingValues(),
    onEvent: (WeekUiEvent) -> Unit = {},
    onCardClick: () -> Unit = {},
    onCopied: () -> Unit = {},
) {

    LaunchedEffect(searchGroupName) {
        onEvent(WeekUiEvent.OnGroupSearch(searchGroupName))
    }

    AnimatedContent(
        targetState = uiState,
        label = "Animated content"
    ) { targetState ->
        when {

            targetState.isLoading -> LoadingContent(modifier = Modifier.fillMaxSize())

            targetState.isEmptyLessonList -> CenteredContentBox(
                option = ContentContainerOption.EmptyLessons(),
                onCardClick = onCardClick
            )

            targetState.isLoadingLessonsError -> CenteredContentBox(
                option = ContentContainerOption.LoadingError(
                    optionalSecondText = targetState.errorMessage,
                    onCopied = onCopied
                ),
                onCardClick = onCardClick
            )

            targetState.daysWithFilteredLessons.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    uiState.daysWithFilteredLessons.forEach { day ->
                        DayItem(day = day)
                    }
                }
            }

        }
    }

}

@Composable
private fun BasedWeekScreenPreview() {
    GSTOUTimetableTheme {
        val lesson = Lesson(
            lessonId = 1,
            name = "Программирование",
            teacher = "Иванов И.И.",
            auditorium = "301",
            groupNames = listOf("ИВТ-21", "ПИ-21"),
            timeInterval = TimeInterval("9:00", "10:20"),
            activityType = "Лекция",
            period = 1,
            dayOfWeek = 1,
            week = 1,
            subgroupNumber = 0
        )
        val lesson2 = Lesson(
            lessonId = 2,
            name = "История",
            teacher = "Иванов И.И.",
            auditorium = "302",
            groupNames = listOf("ИВТ-21"),
            timeInterval = TimeInterval("10:30", "11:50"),
            activityType = "Практика",
            period = 2,
            dayOfWeek = 1,
            week = 1,
            subgroupNumber = 0
        )

        val day = Day(
            name = "Понедельник",
            lessons = listOf(lesson, lesson2)
        )

        val listDay = listOf(
            day,
            day.copy(name = "Вторник"),
            day.copy(name = "Среда"),
            day.copy(name = "Четверг"),
            day.copy(name = "Пятница"),
            day.copy(name = "Суббота"),
            day.copy(name = "Воскресенье"),
        )
        val weekUiState = WeekUiState(days = listDay, daysWithFilteredLessons = listDay)
        Surface {
            WeekContent(uiState = weekUiState)
        }
    }
}

@Preview(
    name = "light_mode",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "ru"
)
@Composable
private fun WeekScreenLightPreview() {
    BasedWeekScreenPreview()
}

@Preview(
    name = "night_mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun WeekScreenNightPreview() {
    BasedWeekScreenPreview()
}
