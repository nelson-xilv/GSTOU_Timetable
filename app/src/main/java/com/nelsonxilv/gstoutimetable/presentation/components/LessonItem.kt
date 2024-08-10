package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape

private const val MaxLinesTitle = 2
private const val MaxLinesText = 1

@Composable
fun LessonItem(
    lesson: Lesson,
    modifier: Modifier = Modifier,
    shape: Shape = DefaultShape
) {
    Card(
        shape = shape,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${lesson.period}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_large)))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        R.string.lesson_name_and_teacher_name,
                        lesson.name,
                        lesson.teacher
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = MaxLinesTitle,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = stringResource(
                        R.string.activity_type_and_auditorium,
                        lesson.activityType,
                        lesson.auditorium
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = MaxLinesText,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_large)))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = lesson.timeInterval.start,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = lesson.timeInterval.end,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
