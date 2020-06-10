package com.example.alcoholcounter

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainApp : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var dataHandler : DataHandler
        lateinit var fusedLocationProviderClient: FusedLocationProviderClient

        // Na vice mistech mate ten warning, ze necheckujete permission. Kdyz ji uzivatel zakaze, crashne vam appka (nasimulovano).
        fun getCurrentLocation(callback: (Location) -> Unit) {
            Handler().postDelayed({
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if(location != null){
                        callback.invoke(location)
                    }
            }}, 500)
        }

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext     // Application implementuje context, nemusite si ho tu ukladat jeste zvlast, staci pouzit instanci MainApp
        dataHandler = DataHandler(applicationContext)
        dataHandler.loadEvents()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)
    }

}