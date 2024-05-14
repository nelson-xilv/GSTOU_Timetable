package com.nelsonxilv.gstoutimetable.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape
import com.nelsonxilv.gstoutimetable.presentation.theme.FirstShape
import com.nelsonxilv.gstoutimetable.presentation.theme.LastShape

@Composable
fun ScheduleTable(lessons: List<FakeLesson>) {
    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .fillMaxSize()
    ) {
        itemsIndexed(lessons) { index, lesson ->
            ScheduleItem(
                lessonNumber = index + 1,
                lesson = lesson,
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth(),
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
    val fakeLesson1 = FakeLesson(
        1,
        "Психология",
        "Солдатский Л.В.",
        "9:00\n10:20"
    )
    val fakeLesson2 = FakeLesson(
        1,
        "Английский язык",
        "Катровский И.С.",
        "10:30\n11:50"
    )
    val fakeLesson3 = FakeLesson(
        1,
        "История",
        "Штефанов А.А.",
        "13:00\n14:20"
    )
    val fakeLesson4 = FakeLesson(
        1,
        "Программирование",
        "Эванс Н.Х.",
        "14:30\n15:50"
    )

    val list = listOf(fakeLesson1, fakeLesson2, fakeLesson3, fakeLesson4)
    ScheduleTable(list)
}

data class FakeLesson(
    val id: Int,
    val name: String,
    val teacher: String,
    val time: String
)