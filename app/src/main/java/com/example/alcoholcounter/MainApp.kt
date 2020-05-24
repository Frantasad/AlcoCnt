package com.example.alcoholcounter

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainApp : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var dataHandler : DataHandler
        lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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