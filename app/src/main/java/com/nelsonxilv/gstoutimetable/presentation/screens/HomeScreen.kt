package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.R
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun HomeScreen(
    timetableUiState: TimetableUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (timetableUiState) {
        is TimetableUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is TimetableUiState.Success -> ResultScreen(
            date = timetableUiState.date,
            timetable = timetableUiState.lessons,
            modifier = modifier.fillMaxWidth(),
            contextPadding = contentPadding
        )

        is TimetableUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(
    date: String,
    timetable: String,
    modifier: Modifier = Modifier,
    contextPadding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contextPadding)
    ) {
        Text(
            text = date,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = timetable,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = "Loading, please wait..."
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_img),
            contentDescription = ""
        )
        Text(
            text = "Loading failed",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    GSTOUTimetableTheme {
        HomeScreen(
            timetableUiState = TimetableUiState.Success(
                "22 March, 2023",
                "Placeholder result text"
            )
        )
    }
}