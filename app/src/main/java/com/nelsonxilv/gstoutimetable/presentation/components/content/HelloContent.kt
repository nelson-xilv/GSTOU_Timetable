package com.nelsonxilv.gstoutimetable.presentation.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nelsonxilv.gstoutimetable.R

@Composable
fun HelloContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_groups_img),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.hello_there),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        )
    }
}