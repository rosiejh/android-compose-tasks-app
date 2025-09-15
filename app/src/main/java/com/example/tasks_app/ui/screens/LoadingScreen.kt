package com.example.tasks_app.ui.screens

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface {
        Text(text = "Loading...")
    }
}
