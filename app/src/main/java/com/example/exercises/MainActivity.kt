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
    private var accelerometer: Sensor? = null

    private lateinit var accelGravity: MutableMap<Int, Double?>
    private lateinit var accelLin: MutableMap<Int, Double?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accelGravity = mutableMapOf<Int, Double?>()
        accelLin = mutableMapOf<Int, Double?>()

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
            this.accelerometer = it
            Log.d("sensor", "$accelerometer")
            Log.d("sensor", "Sensor.TYPE_ACCELEROMETER: ${Sensor.TYPE_ACCELEROMETER}")
        }

    }

    override fun onResume(){
        super.onResume()
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {

        when (event?.sensor?.type) {
            accelerometer?.type -> {
                Log.d("sensor", "accelerometer changed: $accelerometer")
                if(event != null){

                    /*
                    Log.d("sensor","SensorX changed: ${event.values[0]}")
                    Log.d("sensor","SensorY changed: ${event.values[1]}")
                    Log.d("sensor","SensorZ changed: ${event.values[2]}")
*/
                    this.tvAccelerometerX.text = "X: ${event.values[0]}"
                    this.tvAccelerometerY.text = "Y: ${event.values[1]}"
                    this.tvAccelerometerZ.text = "Z: ${event.values[2]}"

                }

            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sensor-changed", "At sensor: $sensor, accuracy: $accuracy")
    }


}

