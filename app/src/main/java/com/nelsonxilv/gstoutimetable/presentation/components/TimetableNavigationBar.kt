package com.nelsonxilv.gstoutimetable.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun TimetableNavigationBar(
    items: List<String>,
    selectedItem: Int = 0,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowInsets)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(50))
            .height(52.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(4.dp),
    ) {
        val width = maxWidth / items.size
        val offset by animateDpAsState(
            targetValue = width * selectedItem,
            label = "Offset"
        )

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = offset.roundToPx(), y = 0)
                }
                .fillMaxHeight()
                .width(width)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                TimetableNavBarItem(
                    text = item,
                    selected = selectedItem == index,
                    onClick = { onItemClick(index) }
                )
            }
        }
    }
}

@Composable
fun RowScope.TimetableNavBarItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .weight(weight = 1f)
            .fillMaxSize()
            .clip(RoundedCornerShape(50))
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "light_mode",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun AnimatedTimetableNavigationBarPreview() {
    GSTOUTimetableTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf("Сегодня", "Завтра", "Неделя")

            TimetableNavigationBar(
                items = items,
                selectedItem = selectedItem,
                onItemClick = { selectedItem = it }
            )
        }
    }
}

@Preview(
    name = "night_mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun AnimatedTimetableNavigationBarNightPreview() {
    GSTOUTimetableTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf("Сегодня", "Завтра", "Неделя")

            TimetableNavigationBar(
                items = items,
                selectedItem = selectedItem,
                onItemClick = { selectedItem = it }
            )
        }
    }
}