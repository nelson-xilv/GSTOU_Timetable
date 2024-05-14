package com.nelsonxilv.gstoutimetable.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape

@Composable
fun ScheduleItem(
    lessonNumber: Int,
    lesson: FakeLesson,
    modifier: Modifier = Modifier,
    shape: Shape = DefaultShape
) {
    Card(
        shape = shape,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = lessonNumber.toString(),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = dimensionResource(id = R.dimen.padding_large))
            )
            Column {
                Text(
                    text = lesson.name,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text = lesson.teacher)
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = lesson.time
            )
        }
    }
}

@Preview
@Composable
fun ScheduleItemPreview() {
    val fakeLesson = FakeLesson(
        1,
        "Психология",
        "Солдатский Л.В.",
        "9:00\n10:20"
    )
    ScheduleItem(1, lesson = fakeLesson)
}