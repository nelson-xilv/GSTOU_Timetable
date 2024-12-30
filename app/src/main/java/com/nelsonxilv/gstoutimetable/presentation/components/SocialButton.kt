package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import com.nelsonxilv.gstoutimetable.R

@Composable
fun SocialButton(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(
            painter = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(width = dimensionResource(id = R.dimen.padding_medium)))
        Text(
            text = text
        )
    }
}
