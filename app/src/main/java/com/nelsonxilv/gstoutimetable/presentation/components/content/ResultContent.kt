package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import com.nelsonxilv.gstoutimetable.presentation.components.LessonItem
import com.nelsonxilv.gstoutimetable.presentation.components.UniversalList

@Composable
fun ResultContent(
    lessons: List<Lesson>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        UniversalList(
            listElements = lessons,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            dynamicCorner = true
        ) { lesson, shape ->
            LessonItem(
                lesson = lesson,
                shape = shape,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}
