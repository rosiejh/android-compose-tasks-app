package com.example.tasks_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.tasks_app.ui.screens.AllTasksScreen
import com.example.tasks_app.ui.screens.NewTaskButton
import com.example.tasks_app.ui.screens.TaskUiState
import com.example.tasks_app.ui.screens.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TasksTopAppBar(scrollBehavior = scrollBehavior) },
        floatingActionButton = {
            NewTaskButton(
                onClick = {
                    println("New Task")
                }
            )
        }
    ) { innerPaddingOuterScaffold ->
        Surface( modifier = Modifier.fillMaxSize()) {
            val tasksViewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory)
            val taskUiState by tasksViewModel.taskUiState.collectAsState()
            AllTasksScreen(
                taskUiState,
                retryAction = tasksViewModel::getAllTasks,
                modifier = Modifier.padding(innerPaddingOuterScaffold)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "All Tasks",
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        modifier = modifier
    )
}