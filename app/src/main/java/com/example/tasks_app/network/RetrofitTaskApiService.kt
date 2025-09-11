package com.example.tasks_app.network

import com.example.tasks_app.model.Task
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetrofitTaskApiService {
    @GET("tasks")
    suspend fun getAllTasks(): List<Task>

    @GET("tasks/by-name/{name}")
    suspend fun getTaskByName(@Path("name") name: String): Task?

    @GET("tasks/by-priority/{priority}")
    suspend fun getTasksByPriority(@Path("priority") priority: String): List<Task>

    @POST("tasks")
    suspend fun addTask(task: Task)

    @DELETE("delete-task/{name}")
    suspend fun deleteTask(@Path("name") name: String)

}
