package com.nelsonxilv.gstoutimetable.presentation.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val FirstShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 4.dp,
    bottomEnd = 4.dp
)

val LastShape = RoundedCornerShape(
    topStart = 4.dp,
    topEnd = 4.dp,
    bottomStart = 20.dp,
    bottomEnd = 20.dp
)

val MediumShape = RoundedCornerShape(corner = CornerSize(4.dp))

val DefaultShape = RoundedCornerShape(corner = CornerSize(20.dp))