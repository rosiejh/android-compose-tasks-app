package com.example.tasks_app.ui.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope // Import for viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tasks_app.TaskApplication
import com.example.tasks_app.data.TaskRepository
import com.example.tasks_app.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// https://composables.com/blog/viewmodels-in-jetpack-compose

// Navigation:
// https://android-developers.googleblog.com/2025/05/announcing-jetpack-navigation-3-for-compose.html
// https://github.com/android/nav3-recipes

sealed interface AllTasksUiState {
    data class Success(val tasks: List<Task>) : AllTasksUiState
    object Error : AllTasksUiState
    object Loading : AllTasksUiState
}

sealed interface SingleTaskUiState {
    data class Success(val task: Task) : SingleTaskUiState
    object Error : SingleTaskUiState
    object Loading : SingleTaskUiState
}

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _allTasksUiState = MutableStateFlow<AllTasksUiState>(AllTasksUiState.Loading)
    val allTasksUiState: StateFlow<AllTasksUiState> = _allTasksUiState.asStateFlow()

    private val _singleTaskUiState = MutableStateFlow<SingleTaskUiState>(SingleTaskUiState.Loading)
    val singleTaskUiState: StateFlow<SingleTaskUiState> = _singleTaskUiState.asStateFlow()

//    private val _singleTaskName = MutableStateFlow<String>("cleaning")
//    val singleTaskName: StateFlow<String> = _singleTaskName.asStateFlow()
//
//    fun updateSingleTaskName(taskName: String) {
//        _singleTaskName.value = taskName
//    }

    init {
        getAllTasks()
//        getSingleTask("cleaning")
    }

    fun getAllTasks() {
        println("In taskViewModel.getAllTasks before launch")
        viewModelScope.launch {
            _allTasksUiState.value = AllTasksUiState.Loading
            println("In taskViewModel.getAllTasks, loading")
            _allTasksUiState.value = try {
                println("In taskViewModel.getAllTasks, getting all tasks")
                AllTasksUiState.Success(taskRepository.getAllTasks())
            } catch (e: Exception) {
                println(e)
                AllTasksUiState.Error
            }
        }
    }

    fun getSingleTask(taskName: String) {
        println("In taskViewModel.getSingleTask before launch")
        viewModelScope.launch {
            _singleTaskUiState.value = SingleTaskUiState.Loading

            _singleTaskUiState.value = try {
                val task = taskRepository.getTaskByName(taskName)
                println("In taskViewModel.getSingleTask, task $task")

                SingleTaskUiState.Success(task!!)

            } catch (e: Exception) {
                println(e)
                SingleTaskUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TaskApplication)
                val taskRepository = application.container.taskRepository
                TaskViewModel(taskRepository = taskRepository)
            }
        }
    }
}