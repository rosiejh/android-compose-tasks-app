package com.example.tasks_app

import android.app.Application
import com.example.tasks_app.data.AppContainer
import com.example.tasks_app.data.DefaultAppContainer

class TaskApplication : Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
