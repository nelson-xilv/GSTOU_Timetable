package com.nelsonxilv.gstoutimetable.presentation.screens.week

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.components.SocialButton
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

private const val TelegramLink = "https://t.me/GSTOU_Timetable"
private const val GitHubLink = "https://github.com/nelson-xilv/GSTOU_Timetable"
private const val GradientOffset = 0f

@Composable
fun WeekScreen(contentPadding: PaddingValues = PaddingValues()) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_large))
        ) {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            ),
                            start = Offset(GradientOffset, Float.POSITIVE_INFINITY),
                            end = Offset(Float.POSITIVE_INFINITY, GradientOffset)
                        )
                    )
                    .padding(all = dimensionResource(id = R.dimen.padding_max))
            ) {
                Text(
                    text = stringResource(id = R.string.soon),
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(
                    modifier = Modifier
                        .height(height = dimensionResource(id = R.dimen.padding_small_medium))
                )

                Text(
                    text = stringResource(id = R.string.app_update_info_message),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier
                        .height(height = dimensionResource(id = R.dimen.padding_medium))
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    SocialButton(
                        icon = painterResource(id = R.drawable.ic_telegram),
                        text = stringResource(id = R.string.telegram),
                        onClick = { openApp(context, TelegramLink) }
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = dimensionResource(id = R.dimen.padding_medium))
                    )
                    SocialButton(
                        icon = painterResource(id = R.drawable.ic_github),
                        text = stringResource(id = R.string.github),
                        onClick = { openApp(context, GitHubLink) }
                    )
                }
            }
        }
    }
}

private fun openApp(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}

@Preview(
    name = "light_mode",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "ru"
)
@Composable
private fun WeekScreenPreview() {
    GSTOUTimetableTheme {
        Surface {
            WeekScreen(
                contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_max))
            )
        }
    }
}

@Preview(
    name = "night_mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun WeekScreenNightPreview() {
    GSTOUTimetableTheme {
        Surface {
            WeekScreen(
                contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_max))
            )
        }
    }
}
