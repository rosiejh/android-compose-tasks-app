package com.example.tasks_app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasks_app.model.Task
import java.util.Locale

@Composable
fun AllTasksScreen(
    taskUiState: TaskUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (taskUiState) {
        is TaskUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )
        is TaskUiState.Success -> TasksListScreen(
            tasks = taskUiState.tasks,

            modifier = modifier.fillMaxWidth()
        )
        is TaskUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface {
        Text(text = "Loading...")
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier) {
    Surface {
        Text(text = "Error")
    }
}

@Composable
fun TasksListScreen(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = tasks, key = { task -> task.name }) { task ->
            TaskCard(
                task = task,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }

}

// https://medium.com/@acceldia/jetpack-compose-creating-expandable-cards-with-content-9ea1eae09efe
@Composable
fun TaskCard(task: Task, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.clickable(onClick = { expanded = !expanded }).padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = task.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            modifier = Modifier.padding(8.dp, 8.dp, 8.dp),
            style = MaterialTheme.typography.titleLarge)
        Text(
            text = task.priority,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.tertiary
        )

        if (expanded) {
            Text(
                text = task.description,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun NewTaskButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Task")
    }
}

