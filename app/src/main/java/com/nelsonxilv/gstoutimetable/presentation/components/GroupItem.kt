package com.nelsonxilv.gstoutimetable.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.presentation.theme.DefaultShape

@Composable
fun GroupItem(
    group: Group,
    onGroupItemClick: (String) -> Unit,
    onClearIconButtonCLick: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DefaultShape
) {
    Card(
        shape = shape,
        onClick = { onGroupItemClick(group.groupName) },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large),
                    vertical = dimensionResource(id = R.dimen.padding_medium)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_history),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_large)))

            Text(
                text = group.groupName,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { onClearIconButtonCLick(group.groupName) }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        }
    }
}
