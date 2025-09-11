package com.example.tasks_app.data

import com.example.tasks_app.network.KtorApiClient
import com.example.tasks_app.network.RetrofitApiClient
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

interface AppContainer {
    
    val baseUrl: String
    val taskRepository: TaskRepository
}

class DefaultAppContainer : AppContainer {
    
    private val useKtor = true
    override val baseUrl = "http://10.0.2.2:8080/"
    
    override val taskRepository: TaskRepository by lazy {
        if (useKtor) {
            val ktorApiClient = KtorApiClient()
            val ktorHttpClient: HttpClient = ktorApiClient.client
            KtorTaskRepository(httpClient = ktorHttpClient, baseUrl = baseUrl)
        } else {
            val retrofitApiClient = RetrofitApiClient(baseUrl = baseUrl)
            val retrofitService = retrofitApiClient.service
            RetrofitTaskRepository(
                retrofitService,
                baseUrl = baseUrl
            )
        }
    }
}
