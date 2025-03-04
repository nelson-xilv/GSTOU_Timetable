package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.presentation.components.LessonItem
import com.nelsonxilv.gstoutimetable.presentation.components.ShapedList

@Composable
fun ResultContent(
    lessons: List<Lesson>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ShapedList(
            listElements = lessons,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
        ) { lesson, shape ->
            LessonItem(
                lesson = lesson,
                shape = shape
            )
        }
    }
}
