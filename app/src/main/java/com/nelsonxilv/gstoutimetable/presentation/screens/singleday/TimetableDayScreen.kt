package com.nelsonxilv.gstoutimetable.presentation.screens.singleday

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.presentation.components.InfoBar
import com.nelsonxilv.gstoutimetable.presentation.components.content.ContentContainer
import com.nelsonxilv.gstoutimetable.presentation.components.content.ContentContainerOption
import com.nelsonxilv.gstoutimetable.presentation.components.content.ResultContent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiEvent
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.TimetableDayUiState
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun TimetableDayScreen(
    searchGroupName: String?,
    dateType: DateType,
    contentPadding: PaddingValues,
    onCardClick: () -> Unit,
) {
    val viewModel =
        hiltViewModel<TimetableDayViewModel, TimetableDayViewModel.Factory>(
            creationCallback = { factory -> factory.create(dateType) }
        )
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    TimetableDayContent(
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
private fun TimetableDayContent(
    uiState: TimetableDayUiState = TimetableDayUiState(),
    searchGroupName: String = "",
    contentPadding: PaddingValues = PaddingValues(),
    onEvent: (TimetableDayUiEvent) -> Unit = {},
    onCardClick: () -> Unit = {},
    onCopied: () -> Unit = {},
) {

    LaunchedEffect(searchGroupName) {
        onEvent(TimetableDayUiEvent.OnGroupSearch(searchGroupName))
    }

    AnimatedContent(
        targetState = uiState,
        label = "Animated content"
    ) { targetState ->
        when {
            targetState.isLoading -> ContentContainer(
                infoBarState = targetState.getInfoBarState(),
                isLoading = true,
                contentPadding = contentPadding,
            )

            targetState.isInitialState -> ContentContainer(
                infoBarState = targetState.getInfoBarState(),
                option = ContentContainerOption.Greeting(),
                onFilterChipClick = { onEvent(TimetableDayUiEvent.OnSubgroupChipClick(it)) },
                onCardClick = onCardClick,
                contentPadding = contentPadding,
            )

            targetState.isEmptyLessonList -> ContentContainer(
                infoBarState = targetState.getInfoBarState(),
                option = ContentContainerOption.EmptyLessons(),
                onFilterChipClick = { onEvent(TimetableDayUiEvent.OnSubgroupChipClick(it)) },
                onCardClick = onCardClick,
                contentPadding = contentPadding,
            )

            targetState.isLoadingLessonsError -> ContentContainer(
                infoBarState = targetState.getInfoBarState(),
                option = ContentContainerOption.LoadingError(
                    errorMessage = targetState.errorMessage,
                    onCopied = onCopied
                ),
                onFilterChipClick = { onEvent(TimetableDayUiEvent.OnSubgroupChipClick(it)) },
                onCardClick = onCardClick,
                contentPadding = contentPadding,
            )

            targetState.filteredLessons.isNotEmpty() -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                InfoBar(
                    state = targetState.getInfoBarState(),
                    onFilterChipClick = { groupNum ->
                        onEvent(TimetableDayUiEvent.OnSubgroupChipClick(groupNum))
                    }
                )

                ResultContent(
                    lessons = targetState.filteredLessons,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimetableDayContentPreview() {
    GSTOUTimetableTheme {
        TimetableDayContent()
    }
}