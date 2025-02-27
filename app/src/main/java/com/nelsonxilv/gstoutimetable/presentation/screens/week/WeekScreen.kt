package com.nelsonxilv.gstoutimetable.presentation.screens.week

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.domain.entity.Day
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.domain.entity.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.components.DayItem
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun WeekScreen(contentPadding: PaddingValues = PaddingValues()) {

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
        period = 1,
        name = "Понедельник",
        lessons = listOf(lesson, lesson2)
    )

    val listDay = listOf(
        day,
        day.copy(period = 2, name = "Вторник"),
        day.copy(period = 2, name = "Среда"),
        day.copy(period = 2, name = "Четверг"),
        day.copy(period = 2, name = "Пятница"),
        day.copy(period = 2, name = "Суббота"),
        day.copy(period = 2, name = "Воскресенье"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(state = rememberScrollState())
    ) {
        listDay.forEach { day ->
            DayItem(day = day)
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
private fun WeekScreenPreview() {
    GSTOUTimetableTheme {
        Surface {
            WeekScreen(
                contentPadding = PaddingValues()
            )
        }
    }
}

@Preview(
    name = "night_mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun WeekScreenNightPreview() {
    GSTOUTimetableTheme {
        Surface {
            WeekScreen(
                contentPadding = PaddingValues()
            )
        }
    }
}
