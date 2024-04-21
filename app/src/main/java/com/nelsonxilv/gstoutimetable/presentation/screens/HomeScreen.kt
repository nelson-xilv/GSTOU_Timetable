package com.nelsonxilv.gstoutimetable.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme

@Composable
fun HomeScreen(
    timetableUiState: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ResultScreen(timetableUiState, modifier.padding(top = contentPadding.calculateTopPadding()))
}

@Composable
fun ResultScreen(timetable: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = timetable)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    GSTOUTimetableTheme {
        ResultScreen("Placeholder result text")
    }
}