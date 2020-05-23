package com.example.alcoholcounter

import android.app.Application
import android.content.Context

class MainApp : Application() {
    companion object {
        var appContext: Context? = null
            private set

        var dataHandler : DataHandler? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        dataHandler = DataHandler(applicationContext)
    }
}