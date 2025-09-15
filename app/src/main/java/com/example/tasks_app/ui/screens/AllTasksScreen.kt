package com.example.tasks_app.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.tasks_app.model.Task
import com.example.tasks_app.ui.TasksApp
import com.example.tasks_app.ui.screens.components.MiniTaskCard
import com.example.tasks_app.ui.theme.TaskAppTheme

@Composable
fun AllTasksScreen(
    taskUiState: AllTasksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onTaskClick: (String) -> Unit
) {
    when (taskUiState) {
        is AllTasksUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )
        is AllTasksUiState.Success -> TasksListScreen(
            tasks = taskUiState.tasks,
            onTaskClick = onTaskClick,
            modifier = modifier.fillMaxWidth(),
        )
//        is AllTasksUiState.Success -> FillerScreen(modifier = modifier.fillMaxSize())
        is AllTasksUiState.Error -> AllTasksErrorScreen (
            retryAction = retryAction,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
fun TasksListScreen(
    tasks: List<Task>,
    onTaskClick: (taskName: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = tasks, key = { task -> task.name }) { task ->
            MiniTaskCard(
                task = task,
                onTaskClick = { onTaskClick(task.name)},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
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


// https://developer.android.com/develop/ui/compose/tooling/previews
class TasksPreviewParameterProvider : PreviewParameterProvider<List<Task>> {
    override val values = sequenceOf(
        listOf(Task("Sample Task 1", "Low", "This is a sample task"),
        Task("Sample Task 2", "High", "This is another sample task"),
        Task("Sample Task 3", "Medium", "Important third task"),
        Task("Sample Task 4", "Low", "Test Task 4, very nice"),
        Task("Sample Task 5", "Critical", "Number 5 Task"))
    )
}

// Idea: Make a Preview for the ViewModel
// Then pass this as a parameter
//
//@Preview(showBackground = true)
//@Composable
//fun TasksLightThemePreview(
//    @PreviewParameter(TasksPreviewParameterProvider::class) tasks: List<Task>
//) {
//    AllTasksScreen(
//        taskUiState = AllTasksUiState.Success(tasks),
//        retryAction = {},
//        modifier = Modifier.fillMaxSize(),
//        onTaskClick,
//    )
//}


//@Preview(showBackground = true)
//@Composable
//fun TasksLightThemePreview(
//    @PreviewParameter(TasksPreviewParameterProvider::class) tasks: List<Task>
//) {
//    TaskAppTheme(darkTheme = false) {
//        TasksApp() {
//            AllTasksScreen(
//                taskUiState = TaskUiState.Success(tasks),
//                retryAction = {},
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//}

@Preview
@Composable
fun TasksDarkThemePreview() {
    TaskAppTheme(darkTheme = true) {
        TasksApp()
    }
}
