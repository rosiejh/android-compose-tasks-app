package com.example.tasks_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tasks_app.ui.screens.AllTasksScreen
import com.example.tasks_app.ui.screens.NewTaskButton
import com.example.tasks_app.ui.screens.SingleTaskScreen
import com.example.tasks_app.ui.screens.TaskViewModel
import kotlinx.serialization.Serializable

// Looking at this Codelab for multiscreen app navigation
// https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#0
// https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/tree/starter

// This was most useful for navigation
// https://developer.android.com/develop/ui/compose/navigation


@Serializable
data object AllTasksRoute

@Serializable
data class SingleTaskRoute(val taskName: String)

fun resolveRouteTitle(route: String?): String {
    return if (route == null) {
        "Mystery Page"
    } else if (route.split(".").last().contains("AllTasks")) {
        "All Tasks"
    } else if (route.split(".").last().contains("SingleTask")) {
        "Task Detail"
    } else {
        "Mystery Page"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksApp() {
    val navController = rememberNavController()
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TasksTopAppBar(
                scrollBehavior = scrollBehavior,
                currentScreenTitle = resolveRouteTitle(navController.currentBackStackEntryAsState().value?.destination?.route),
                modifier = Modifier
            )
        },
        // TODO make FABs for each screen?
        floatingActionButton = {
            NewTaskButton(
                onClick = {
                    println("New Task")
                }
            )
        }
    ) {
        innerPadding ->
        AppNavigation(
            navController = navController,
            viewModel = taskViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TaskViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AllTasksRoute,
        modifier = modifier
    ) {
        composable<AllTasksRoute> {
            val allTasksUiState by viewModel.allTasksUiState.collectAsState()
            println("Starting the all tasks screen")
            println("Current route? ${navController.currentBackStackEntryAsState().value?.destination?.route}")
            AllTasksScreen(
                taskUiState = allTasksUiState,
                retryAction = viewModel::getAllTasks,
                modifier = Modifier.fillMaxSize(),
                onTaskClick = { taskName ->
                    navController.navigate(SingleTaskRoute(taskName))
                }
            )
        }
        composable<SingleTaskRoute> { backStackEntry ->
            val singleTaskRoute: SingleTaskRoute = backStackEntry.toRoute()
            val singleTaskUiState by viewModel.singleTaskUiState.collectAsState()
            val taskName = singleTaskRoute.taskName
            println("Starting the single task screen with $taskName")
            println("Current route? ${navController.currentBackStackEntryAsState().value?.destination?.route}")
            LaunchedEffect(taskName) {
                viewModel.getSingleTask(taskName)
            }

            SingleTaskScreen(
                taskUiState = singleTaskUiState,
                retryAction = { viewModel.getSingleTask(taskName)  },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreenTitle: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currentScreenTitle,
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        modifier = modifier
    )
}
