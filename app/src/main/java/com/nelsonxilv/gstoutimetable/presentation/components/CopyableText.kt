package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.nelsonxilv.gstoutimetable.R

@Composable
fun CopyableText(
    text: String,
    modifier: Modifier = Modifier,
    onCopied: () -> Unit = {},
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Text(
        text = text,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
            .clickable {
                clipboardManager.setText(AnnotatedString(text))
                onCopied()
            }
    )
}