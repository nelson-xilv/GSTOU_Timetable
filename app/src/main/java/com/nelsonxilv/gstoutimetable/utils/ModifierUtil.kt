package com.nelsonxilv.gstoutimetable.utils

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val Spacing: MarqueeSpacing = MarqueeSpacing.fractionOfContainer(fraction = 1f / 5f)
private val Velocity: Dp = 50.dp

fun Modifier.customMarquee(
    spacing: MarqueeSpacing = Spacing,
    velocity: Dp = Velocity
): Modifier = this.basicMarquee(
    spacing = spacing,
    velocity = velocity
)