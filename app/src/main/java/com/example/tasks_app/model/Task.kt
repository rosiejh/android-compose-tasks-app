package com.example.tasks_app.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val name: String,
    val description: String,
    val priority: String
)