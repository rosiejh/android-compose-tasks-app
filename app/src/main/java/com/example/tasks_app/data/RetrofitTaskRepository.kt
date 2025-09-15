package com.example.tasks_app.data

import com.example.tasks_app.model.Task
import com.example.tasks_app.network.RetrofitTaskApiService

class RetrofitTaskRepository(val retrofitService: RetrofitTaskApiService, val baseUrl: String) :
    TaskRepository {

    override suspend fun getAllTasks(): List<Task> {
        return retrofitService.getAllTasks()
    }

    override suspend fun getTaskByName(name: String): Task? {
        return retrofitService.getTaskByName(name)
    }

    override suspend fun getTasksByPriority(priority: String): List<Task> {
        return retrofitService.getTasksByPriority(priority)
    }

    override suspend fun addTask(task: Task) {
        retrofitService.addTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        retrofitService.deleteTask(task.name)
    }

}