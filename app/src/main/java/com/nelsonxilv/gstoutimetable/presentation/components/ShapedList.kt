package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape
import com.nelsonxilv.gstoutimetable.presentation.theme.FirstShape
import com.nelsonxilv.gstoutimetable.presentation.theme.LastShape
import com.nelsonxilv.gstoutimetable.presentation.theme.MediumShape
import com.nelsonxilv.gstoutimetable.R

@Composable
fun <T> ShapedList(
    listElements: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (element: T, shape: Shape) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(id = R.dimen.padding_small_medium)
        )
    ) {
        listElements.forEachIndexed { index, element ->
            content(
                element,
                getShapeForIndex(index, listElements.size)
            )
        }
    }
}

private fun getShapeForIndex(index: Int, size: Int): Shape {
    return if (size < 2) {
        DefaultShape
    } else {
        when (index) {
            0 -> FirstShape
            size - 1 -> LastShape
            else -> MediumShape
        }
    }
}