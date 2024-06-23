package com.nelsonxilv.gstoutimetable.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.presentation.components.content.EmptyLessonsContent
import com.nelsonxilv.gstoutimetable.presentation.components.content.ErrorContent
import com.nelsonxilv.gstoutimetable.presentation.components.content.LoadingContent
import com.nelsonxilv.gstoutimetable.presentation.components.content.ResultContent

private const val DurationMillis = 400

@Composable
fun HomeScreen(
    timetableUiState: TimetableUiState,
    modifier: Modifier = Modifier,
    onFilterChipClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    AnimatedContent(
        targetState = timetableUiState,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(DurationMillis)
            ) togetherWith fadeOut(animationSpec = tween(DurationMillis))
        },
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            is TimetableUiState.Loading -> LoadingContent(modifier.fillMaxSize())
            is TimetableUiState.Success -> ResultContent(
                uiState = targetState,
                onFilterChipClick = onFilterChipClick,
                modifier = modifier.fillMaxSize(),
                contentPadding = contentPadding
            )

            is TimetableUiState.Error -> ErrorContent(modifier.fillMaxSize())
            is TimetableUiState.EmptyTimetable -> EmptyLessonsContent(modifier.fillMaxSize())
        }
    }
}