package com.example.lab07

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private var locationManager: LocationManager? = null

    private val locationList: MutableList<Location> = mutableListOf()

    private var currentGPSLocation: Location? = null
    private var currentNetworkLocation: Location? = null

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        listView = findViewById(R.id.listItems)
        listView.adapter = LocationListAdapter(this, locationList)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            startTracking()
        } else {
            Toast.makeText(this,
                "Нет разрешения на доступ к геолокации",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        startTracking()
    }

    override fun onPause() {
        super.onPause()
        stopTracking()
    }

    private fun startTracking() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this,"Нет разрешения на доступ к геолокации",
                    Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
        } else {
            locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    10f,
                    locationListener
            )
            locationManager!!.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000,
                    10f,
                    locationListener
            )
            showInfo()
        }
    }

    private fun stopTracking() {
        locationManager!!.removeUpdates(locationListener)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            showInfo(location)
        }

        override fun onProviderDisabled(provider: String) {
            showInfo()
        }

        override fun onProviderEnabled(provider: String) {
            showInfo()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            showInfo()
        }
    }

    private fun showInfo(location: Location? = null) {
        val isGpsOn = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkOn = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        findViewById<TextView>(R.id.gps_status).text = if (isGpsOn) "GPS ON" else "GPS OFF"
        findViewById<TextView>(R.id.network_status).text = if (isNetworkOn) "Network ON"
            else "Network OFF"
        if (location != null) {
            if (location.provider == LocationManager.GPS_PROVIDER) {
                findViewById<TextView>(R.id.gps_coords).text =
                    "GPS: широта = " + location.latitude.toString() +", долгота = " +
                            location.longitude.toString()
                currentGPSLocation = location
            }

            if (location.provider == LocationManager.NETWORK_PROVIDER) {
                findViewById<TextView>(R.id.network_coords).text ="Network: широта = " +
                        location.latitude.toString() +", долгота = " + location.longitude.toString()
                currentNetworkLocation = location
            }
            location.checkNearestLocation()
        }
    }

    fun addLocationToList(view: View){
        if (currentGPSLocation != null) {
            locationList.add(currentGPSLocation!!)
            currentGPSLocation!!.checkNearestLocation()
        }
        else if(currentNetworkLocation != null){
            locationList.add(currentNetworkLocation!!)
            currentNetworkLocation!!.checkNearestLocation()
        }

        (listView.adapter as LocationListAdapter).notifyDataSetChanged()
    }

    private fun Location.checkNearestLocation(){
        var isNearToOtherLocations = false
        for (location in locationList) {
            if (this.distanceTo(location) < 100) {
                isNearToOtherLocations = true
                break
            }
        }
        if (isNearToOtherLocations) {
            findViewById<TextView>(R.id.isNearest).text =
                if (isNearToOtherLocations) "Вы находитесь в радиусе менее 100 м"
                else "Вы находитесь в радиусе более 100 м"
        }
    }

    fun buttonOpenSettings(view: View) {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}