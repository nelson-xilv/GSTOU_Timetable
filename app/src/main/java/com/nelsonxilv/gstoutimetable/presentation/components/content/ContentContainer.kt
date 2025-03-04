package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.components.CopyableText
import com.nelsonxilv.gstoutimetable.presentation.components.InfoBar
import com.nelsonxilv.gstoutimetable.presentation.screens.singleday.contract.InfoBarState
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun ContentContainer(
    option: ContentContainerOption = ContentContainerOption.Greeting(),
    isLoading: Boolean = false,
    infoBarState: InfoBarState? = null,
    contentPadding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    onFilterChipClick: (Int) -> Unit = {},
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        if (infoBarState != null) {
            InfoBar(
                state = infoBarState,
                onFilterChipClick = { onFilterChipClick(it) },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        } else {
            content()
        }

        if (isLoading) {
            LoadingContent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        } else {
            ContentCard(
                option = option,
                onCardClick = onCardClick,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ContentCard(
    option: ContentContainerOption,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(fraction = 0.86f),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = option.iconRes),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(id = option.textRes),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large)
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            if (option is ContentContainerOption.LoadingError) {
                CopyableText(
                    text = option.errorMessage,
                    onCopied = option.onCopied
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            }
        }
    }
}

sealed interface ContentContainerOption {

    @get:DrawableRes
    val iconRes: Int

    @get:StringRes
    val textRes: Int

    data class Greeting(
        @DrawableRes override val iconRes: Int = R.drawable.search_groups_img,
        @StringRes override val textRes: Int = R.string.hello_there,
    ) : ContentContainerOption

    data class EmptyLessons(
        @DrawableRes override val iconRes: Int = R.drawable.sleep_img,
        @StringRes override val textRes: Int = R.string.you_can_relax,
    ) : ContentContainerOption

    data class LoadingError(
        @DrawableRes override val iconRes: Int = R.drawable.error_img,
        @StringRes override val textRes: Int = R.string.loading_failed,
        val errorMessage: String,
        val onCopied: () -> Unit
    ) : ContentContainerOption
}

@Preview(showBackground = true)
@Composable
fun ContentContainerPreview() {
    GSTOUTimetableTheme {
        ContentContainer(
            infoBarState = InfoBarState(),
            option = ContentContainerOption.Greeting()
        )
    }
}