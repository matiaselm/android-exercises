package com.example.exercises

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    /*source: https://expertise.jetruby.com/how-to-implement-motion-sensor-in-a-kotlin-app-b70db1b5b8e5 */
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    private lateinit var accelGravity: Array<Double>
    private lateinit var accelLin: Array<Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
        This operator tries to cast a service instance to a SensorManger-type
        -> returns null if service is not an instance of SensorManager OR getSystemService returned null itself.
         */
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        /**
        Used standard Kotlin library function let{}, in order not to overload the code with null checks. <- a good way to do this when using more than one sensor
        - Check whether the returned sensor is not null
        - Then let executes a block of code on the sensor with it as this particular sensor object
        - In the block we store a reference to the sensor in question
         */
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            // Log.d("sensor", "$accelerometer")
            this.accelerometer = it
            Log.d("sensor", "$accelerometer")
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("sensor-changed", "$event")
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                val alpha = 0.8f

                accelGravity[0] = alpha * accelGravity[0] + (1 - alpha) * event.values[0]
                accelGravity[1] = alpha * accelGravity[1] + (1 - alpha) * event.values[1]
                accelGravity[2] = alpha * accelGravity[2] + (1 - alpha) * event.values[2]

                accelLin[0] = event.values[0] - accelGravity[0]
                accelLin[1] = event.values[1] - accelGravity[1]
                accelLin[2] = event.values[2] - accelGravity[2]

                this.tvAccelerometerX.text = "X: ${accelLin[0].toString()}"
                this.tvAccelerometerY.text = "Y: ${accelLin[1].toString()}"
                this.tvAccelerometerZ.text = "Z: ${accelLin[2].toString()}"
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sensor-changed", "At sensor: $sensor, accuracy: $accuracy")
    }


}

