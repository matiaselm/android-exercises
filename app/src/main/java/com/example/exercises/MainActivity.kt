package com.example.exercises


import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val lm = getSystemService(Context.LOCATION_SERVICE) as
            LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lm.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            60 * 1000,
            50f,
            this
        )

        }
    }

