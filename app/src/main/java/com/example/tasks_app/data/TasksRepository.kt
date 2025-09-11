package com.example.tasks_app.data

import com.example.tasks_app.model.Task
import com.example.tasks_app.network.TaskApiService
import retrofit2.Retrofit


interface TasksRepository {
    suspend fun fetchTasks(): List<Task>
}

class NetworkTasksRepository(private val taskApiService: TaskApiService) : TasksRepository {
    override suspend fun fetchTasks(): List<Task> {
        return taskApiService.getTasks()
    }
}
