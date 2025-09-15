package com.example.tasks_app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasks_app.model.Task
import com.example.tasks_app.ui.screens.components.BigTaskCard

@Composable
fun SingleTaskScreen(
    taskUiState: SingleTaskUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (taskUiState) {
        is SingleTaskUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )

        is SingleTaskUiState.Success -> TaskScreen(
            task = taskUiState.task,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        is SingleTaskUiState.Error ->
            SingleTaskErrorScreen(
                modifier = modifier.fillMaxSize(),
                retryAction = retryAction,
            )
    }
}

@Composable
fun TaskScreen(task: Task, modifier: Modifier) {
    BigTaskCard(
        task = task,
        onTaskClick = {},
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun FillerScreen(modifier: Modifier) {
    Text(text = "Filler", modifier = modifier)
}