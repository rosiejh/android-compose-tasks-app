package com.example.tasks_app.data

import com.example.tasks_app.model.Task


interface TaskRepository {
    suspend fun getAllTasks(): List<Task>

    suspend fun getTaskByName(name: String): Task?

    suspend fun getTasksByPriority(priority: String): List<Task>

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)
}

