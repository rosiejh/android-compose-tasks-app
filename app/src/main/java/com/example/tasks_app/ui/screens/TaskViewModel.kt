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


sealed interface TaskUiState {
    data class Success(val tasks: List<Task>) : TaskUiState
    object Error : TaskUiState
    object Loading : TaskUiState
}

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _taskUiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)

    var taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()

    init {
        getAllTasks()
    }
    fun getAllTasks() {
        viewModelScope.launch {
            _taskUiState.value = TaskUiState.Loading
            _taskUiState.value = try {
                TaskUiState.Success(taskRepository.getAllTasks())
            } catch (e: Exception) {
                TaskUiState.Error
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