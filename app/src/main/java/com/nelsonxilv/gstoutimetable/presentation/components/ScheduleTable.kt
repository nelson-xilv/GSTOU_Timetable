package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape
import com.nelsonxilv.gstoutimetable.presentation.theme.FirstShape
import com.nelsonxilv.gstoutimetable.presentation.theme.LastShape

@Composable
fun ScheduleTable(
    lessons: List<Lesson>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(lessons) { index, lesson ->
            ScheduleItem(
                lesson = lesson,
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                shape = getShapeForIndex(index, lessons.size)
            )
        }
    }
}

private fun getShapeForIndex(index: Int, size: Int): Shape {
    return when (index) {
        0 -> FirstShape
        size - 1 -> LastShape
        else -> DefaultShape
    }
}

@Preview
@Composable
fun ScheduleTablePreview() {
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
        subgroupNumber = 1
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
    ScheduleTable(list)
}