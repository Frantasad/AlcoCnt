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
    private var _lastLocation : Location? = null
    private var _currentUserLocationMarker : Marker? = null
    private val _requestUserLocationCode : Int = 666
    private val _defaultZoom : Float = 16F
    private lateinit var _fusedLocationProviderClient: FusedLocationProviderClient

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission()
        }

        _fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun locationPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkUserLocationPermission() {
        if (!locationPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                _requestUserLocationCode
            )
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
                    if (_googleApiClient == null) {
                        buildGoogleApiClient()
                    }
                    _map?.isMyLocationEnabled = true
                }
                else {
                    TODO()
                    //Toast.makeText(this.requireContext(), "Permission denied...", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    override fun onMapReady(map: GoogleMap?) {
        map?.let { _map = it }

        if (locationPermissionGranted()) {
            buildGoogleApiClient()
            _map!!.isMyLocationEnabled = true
            setMapOnCurrentLocation(_defaultZoom)
        }
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

    override fun onLocationChanged(location: Location?) {
        _lastLocation = location
        _currentUserLocationMarker?.remove()

        val coordinates = LatLng(location!!.latitude, location!!.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(coordinates)
        markerOptions.title("Current location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

        _currentUserLocationMarker = _map?.addMarker(markerOptions)

        _map?.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
        //_map?.animateCamera(CameraUpdateFactory.zoomBy(_defaultZoom))

        if (_googleApiClient != null && _googleApiClient!!.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(_googleApiClient, this)
        }
    }

    private fun setMapOnCurrentLocation(zoom : Float) {
        _fusedLocationProviderClient.lastLocation.addOnCompleteListener(this.requireActivity()) { task ->
            var location: Location? = task.result

            if (location != null) {
                val coordinates = LatLng(location!!.latitude, location!!.longitude)
                moveCamera(coordinates, _defaultZoom)
                /*_map?.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
                _map?.animateCamera(CameraUpdateFactory.zoomBy(zoom))*/

            }
        }
    }

    private fun moveCamera(coordinates: LatLng, zoom: Float) {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom))
    }

    fun getCurrentLocation() : Location? {
        TODO("async sranda")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}
