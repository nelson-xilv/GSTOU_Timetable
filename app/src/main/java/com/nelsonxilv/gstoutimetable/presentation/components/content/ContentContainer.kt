package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
        modifier = modifier,
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
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

@Preview(showBackground = true)
@Composable
fun ContentContainerPreview() {
    GSTOUTimetableTheme {
        ContentContainer(
            iconRes = R.drawable.error_img,
            textRes = R.string.loading_failed,
            optionalSecondText = "HTTP 404 Error message",
            onCardClick = {}
        )
    }
}