package com.example.tasks_app.data

import com.example.tasks_app.model.Task
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


class KtorTaskRepository(val httpClient: HttpClient, val baseUrl: String) : TaskRepository {

    override suspend fun getAllTasks(): List<Task> {
        val url = "${baseUrl}/tasks"
        return httpClient.get(url).body()
    }

    override suspend fun getTaskByName(name: String): Task? {
        val url = "${baseUrl}/tasks/by-name/${name}"
        return httpClient.get(url).body()
    }

    override suspend fun getTasksByPriority(priority: String): List<Task> {
        val url = "${baseUrl}/tasks/by-priority/${priority}"
        return httpClient.get(url).body()
    }

    override suspend fun addTask(task: Task) {
        val url = "${baseUrl}/tasks"
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
    }

    // TODO use same URL as getTaskByName
    override suspend fun deleteTask(task: Task) {
        val url = "${baseUrl}/tasks/delete-task/${task.name}"
        httpClient.delete(url)
    }
}
