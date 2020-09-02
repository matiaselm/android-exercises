package com.example.exercises

import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class LocationActivity() : Activity(), LocationListener {
    override fun onLocationChanged(p0: Location) {
        Log.d("location-lab","new latitude: ${p0.latitude} and longitude: ${p0.longitude}")
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.d("location-lab","Status changed $p0, $p1, $p2 ")
    }

    override fun onProviderEnabled(p0: String) {
        Log.d("location-lab","Provider enabled $p0")
    }

    override fun onProviderDisabled(p0: String) {
        Log.d("location-lab","Provider disabled $p0")
    }
}