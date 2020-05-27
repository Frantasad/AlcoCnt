package com.example.alcoholcounter.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(),
                    OnMapReadyCallback,
                    GoogleApiClient.ConnectionCallbacks,
                    GoogleApiClient.OnConnectionFailedListener,
                    LocationListener {

    private var _map : GoogleMap? = null
    private var _googleApiClient: GoogleApiClient? = null
    private var _locationRequest : LocationRequest? = null
    private val _requestUserLocationCode : Int = 666

    private var _eventMarkers: ArrayList<MarkerOptions> = ArrayList()
    private val _defaultZoom : Float = 16F

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission()
        }

        loadEventMarkers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    private fun locationPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkUserLocationPermission() {
        if (!locationPermissionGranted()) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), _requestUserLocationCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            _requestUserLocationCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && locationPermissionGranted()) {
                    mapInteractionInit()
                } else {
                    Toast.makeText(this.requireContext(), R.string.location_access_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let { _map = it }

        if (locationPermissionGranted()) {
            mapInteractionInit()
        }
    }

    private fun mapInteractionInit() {
        buildGoogleApiClient()
        _map!!.isMyLocationEnabled = true
        MainApp.getCurrentLocation() { location -> setMapOnLocation(location, _defaultZoom) }
        showEventMarkers()
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        _googleApiClient = GoogleApiClient.Builder(MainApp.appContext)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        _googleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        _locationRequest = LocationRequest()
        _locationRequest?.interval = 1000
        _locationRequest?.fastestInterval = 1000
        _locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (locationPermissionGranted())
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(_googleApiClient, _locationRequest, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        setMapOnLocation(location, _defaultZoom)

        if (_googleApiClient != null && _googleApiClient!!.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(_googleApiClient, this)
        }
    }

    private fun setMapOnLocation(location: Location, zoom: Float) {
        val coordinates = LatLng(location.latitude, location.longitude)
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom))
    }

    private fun loadEventMarkers() {
        for (event in MainApp.dataHandler.events) {
            if (event.location != null) {
                val markerOptions = MarkerOptions()
                markerOptions.position(LatLng(event.location!!.first, event.location!!.second))
                markerOptions.title(event.title)
                if (!event.ended) {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                }
                _eventMarkers.add(markerOptions)
            }
        }
    }

    private fun showEventMarkers() {
        for (eventMarker in _eventMarkers) {
            _map?.addMarker(eventMarker)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}
