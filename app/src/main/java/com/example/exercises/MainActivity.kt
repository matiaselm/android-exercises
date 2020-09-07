package com.example.exercises

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity(), SensorEventListener {

    /*sources: https://expertise.jetruby.com/how-to-implement-motion-sensor-in-a-kotlin-app-b70db1b5b8e5
    * https://github.com/wirecube/android_additive_animations */

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var screenHeight = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
        This operator tries to cast a service instance to a SensorManger-type
        -> returns null if service is not an instance of SensorManager OR getSystemService returned null itself.
         */
        this.sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        /*
        Used standard Kotlin library function let{}, in order not to overload the code with null checks. <- a good way to do this when using more than one sensor
        - Check whether the returned sensor is not null
        - Then let executes a block of code on the sensor with it as this particular sensor object
        - In the block we store a reference to the sensor in question
         */

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            this.accelerometer = it
            Log.d("sensor", "$accelerometer")
            Log.d("sensor", "Sensor.TYPE_ACCELEROMETER: ${Sensor.TYPE_ACCELEROMETER}")
        }
    }

    // Point of this function is to apply animation to the screen showing the value according to them
    private fun animate(X: Float, Y: Float, Z: Float, view: View) {

        Log.d("sensor", "animate start")

        val amountToMoveRight = X * -100
        val amountToMoveDown = Y * 100

        AdditiveAnimator.animate(view, 1000)
            .x(amountToMoveRight)
            .y(amountToMoveDown)
            .start()
    }

    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        screenHeight = displayMetrics.heightPixels.toFloat()

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {

        when (event?.sensor?.type) {
            accelerometer?.type -> {
                // Log.d("sensor", "accelerometer changed: $accelerometer")
                if (event != null) {

                    this.tvAccelerometerX.text = "X: ${event.values[0]}"
                    this.tvAccelerometerY.text = "Y: ${event.values[1]}"
                    this.tvAccelerometerZ.text = "Z: ${event.values[2]}"

                    // Animate gives sensor's value to the additive animator and it moves the box with it
                    animate(event.values[0], event.values[1], event.values[2], box)
                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sensor-changed", "At sensor: $sensor, accuracy: $accuracy")
    }


}

