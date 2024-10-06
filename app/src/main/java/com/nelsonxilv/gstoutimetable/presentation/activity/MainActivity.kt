package com.nelsonxilv.gstoutimetable.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nelsonxilv.gstoutimetable.presentation.screens.main.TimetableApp
import com.nelsonxilv.gstoutimetable.presentation.theme.GSTOUTimetableTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            GSTOUTimetableTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TimetableApp()
                }
            }
        }
    }
}