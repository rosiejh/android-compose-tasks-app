package com.example.tasks_app.network

import com.example.tasks_app.model.Task
import retrofit2.Retrofit
import retrofit2.http.GET


interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
}
