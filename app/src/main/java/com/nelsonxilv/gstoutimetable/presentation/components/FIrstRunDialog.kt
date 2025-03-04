package com.nelsonxilv.gstoutimetable.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

private const val TelegramTextForLink = "Telegram"
private const val GitHubTextForLink = "GitHub"
private const val TelegramLink = "https://t.me/GSTOU_Timetable"
private const val GitHubLink = "https://github.com/nelson-xilv/GSTOU_Timetable"

@Composable
fun FirstRunDialog(onConfirmation: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_hello_filled),
                contentDescription = null
            )
        },
        title = { Text(text = stringResource(id = R.string.welcome)) },
        text = { LinkText() },
        onDismissRequest = onConfirmation,
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(text = stringResource(id = R.string.good))
            }
        }
    )
}

@Composable
private fun LinkText() {
    val message = stringResource(id = R.string.app_update_info_message)
    val annotatedString = buildAnnotatedString {
        val telegramStartIndex = message.indexOf(TelegramTextForLink)
        val telegramEndIndex = telegramStartIndex + TelegramTextForLink.length

        val githubStartIndex = message.indexOf(GitHubTextForLink)
        val githubEndIndex = githubStartIndex + GitHubTextForLink.length

        append(message.substring(0, telegramStartIndex))

        withLink(
            link = LinkAnnotation.Url(
                url = TelegramLink,
                styles = TextLinkStyles(
                    style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                )
            )
        ) {
            append(message.substring(telegramStartIndex, telegramEndIndex))
        }

        append(message.substring(telegramEndIndex, githubStartIndex))

        withLink(
            link = LinkAnnotation.Url(
                url = GitHubLink,
                styles = TextLinkStyles(
                    style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                )
            )
        ) {
            append(message.substring(githubStartIndex, githubEndIndex))
        }

        append(message.substring(githubEndIndex))
    }

    Text(text = annotatedString)

}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
private fun WeekScreenPreview() {
    GSTOUTimetableTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FirstRunDialog(
                onConfirmation = {}
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
    locale = "ru"
)
@Composable
private fun WeekScreenNightPreview() {
    GSTOUTimetableTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FirstRunDialog(
                onConfirmation = {}
            )
        }
    }
}