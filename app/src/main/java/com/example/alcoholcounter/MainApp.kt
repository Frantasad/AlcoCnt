package com.example.alcoholcounter

import android.app.Application
import android.content.Context

class MainApp : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var dataHandler : DataHandler
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        dataHandler = DataHandler(applicationContext)
        dataHandler!!.loadEvents()
    }
}