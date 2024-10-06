package com.nelsonxilv.gstoutimetable.presentation.screens.singleday

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import com.nelsonxilv.gstoutimetable.presentation.components.TimetableInfoBar
import com.nelsonxilv.gstoutimetable.presentation.components.content.ContentContainer
import com.nelsonxilv.gstoutimetable.presentation.components.content.LoadingContent
import com.nelsonxilv.gstoutimetable.presentation.components.content.ResultContent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.LessonsUiState
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun TimetableOfDayScreen(
    searchGroupName: String,
    dateInfo: DateInfo,
    contentPadding: PaddingValues,
    onCardClick: () -> Unit,
) {
    val viewModel = viewModel<TimetableOfDayViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    TimetableOfDayContent(
        uiState = uiState,
        searchGroupName = searchGroupName,
        dateInfo = dateInfo,
        onEvent = viewModel::handleEvent,
        contentPadding = contentPadding,
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
private fun TimetableOfDayContent(
    uiState: LessonsUiState = LessonsUiState(),
    searchGroupName: String = "",
    dateInfo: DateInfo = DateInfo(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onEvent: (LessonsUiEvent) -> Unit = {},
    onCardClick: () -> Unit = {},
    onCopied: () -> Unit = {},
) {

    LaunchedEffect(searchGroupName) {
        onEvent(LessonsUiEvent.OnGroupSearch(searchGroupName))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        TimetableInfoBar(
            showFilterChips = showFilterChips(uiState),
            selectedSubgroupNumber = uiState.selectedSubgroupNumber,
            date = dateInfo.currentFormattedDate,
            weekNumber = dateInfo.currentWeekNumber,
            onFilterChipClick = { groupNum ->
                onEvent(LessonsUiEvent.OnSubgroupChipClick(groupNum))
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        AnimatedContent(
            targetState = uiState,
            label = "Animated content"
        ) { targetState ->

            when {
                targetState.isLoading -> LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 140.dp)
                )

                targetState.isInitialState -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 140.dp)
                ) {
                    ContentContainer(
                        iconRes = targetState.greetingImageId,
                        textRes = targetState.greetingMessageId,
                        onCardClick = onCardClick,
                        onCopied = onCopied,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                targetState.isEmptyLessonList -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 140.dp)
                ) {
                    ContentContainer(
                        iconRes = targetState.emptyLessonListImageId,
                        textRes = targetState.emptyLessonListMessageId,
                        onCardClick = onCardClick,
                        onCopied = onCopied,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                targetState.isLoadingLessonsError -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 140.dp)
                ) {
                    ContentContainer(
                        iconRes = targetState.loadingLessonsErrorImageId,
                        textRes = targetState.loadingLessonsErrorMessageId,
                        optionalSecondText = targetState.errorMessage,
                        onCardClick = onCardClick,
                        onCopied = onCopied,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                targetState.lessons.isNotEmpty() -> ResultContent(
                    lessons = targetState.lessons,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun showFilterChips(lessonsUiState: LessonsUiState) =
    lessonsUiState.isEmptyLessonList || lessonsUiState.lessons.isNotEmpty()

@Preview(showBackground = true)
@Composable
private fun TimetableOfDayContentPreview() {
    GSTOUTimetableTheme {
        TimetableOfDayContent()
    }
}