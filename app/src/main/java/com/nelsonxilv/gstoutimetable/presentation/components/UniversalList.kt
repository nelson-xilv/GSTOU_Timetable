package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape
import com.nelsonxilv.gstoutimetable.presentation.theme.FirstShape
import com.nelsonxilv.gstoutimetable.presentation.theme.LastShape
import com.nelsonxilv.gstoutimetable.presentation.theme.MediumShape

@Composable
fun <T> UniversalList(
    listElements: List<T>,
    modifier: Modifier = Modifier,
    dynamicCorner: Boolean = false,
    content: @Composable (element: T, shape: Shape) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(listElements) { index, element ->
            content(
                element,
                getShapeForIndex(index, listElements.size, dynamicCorner)
            )
        }
    }
}

private fun getShapeForIndex(index: Int, size: Int, dynamicCorner: Boolean): Shape {
    return if (size < 2 || !dynamicCorner) {
        DefaultShape
    } else {
        when (index) {
            0 -> FirstShape
            size - 1 -> LastShape
            else -> MediumShape
        }
    }
}