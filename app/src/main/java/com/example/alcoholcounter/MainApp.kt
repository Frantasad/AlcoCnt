package com.example.alcoholcounter

import android.app.Application
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainApp : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var dataHandler : DataHandler
        private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

        fun getCurrentLocation(callback: (Location) -> Unit) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                callback.invoke(location!!)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        dataHandler = DataHandler(applicationContext)
        dataHandler!!.loadEvents()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)
    }
    
}