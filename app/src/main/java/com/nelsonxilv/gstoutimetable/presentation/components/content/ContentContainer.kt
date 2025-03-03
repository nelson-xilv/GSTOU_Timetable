package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.components.CopyableText
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun ContentContainer(
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    optionalSecondText: String? = null,
    onCardClick: () -> Unit = {},
    onCopied: () -> Unit = {},
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
                painter = painterResource(id = iconRes),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(id = textRes),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large)
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            if (optionalSecondText != null) {
                CopyableText(
                    text = optionalSecondText,
                    onCopied = onCopied
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            }
        }
    }
}

@Composable
fun CenteredContentBox(
    option: ContentContainerOption,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(fraction = 0.86f),
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
                        text = option.optionalSecondText,
                        onCopied = option.onCopied
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
                }
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
        val optionalSecondText: String,
        val onCopied: () -> Unit
    ) : ContentContainerOption
}

@Preview(showBackground = true)
@Composable
fun ContentContainerPreview() {
    GSTOUTimetableTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 140.dp)
            ) {
                ContentContainer(
                    iconRes = R.drawable.error_img,
                    textRes = R.string.loading_failed,
                    optionalSecondText = LoremIpsum(10)
                        .values
                        .toList()
                        .first()
                        .toString(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}