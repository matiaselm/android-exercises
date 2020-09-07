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
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity(), SensorEventListener {

    /*source: https://expertise.jetruby.com/how-to-implement-motion-sensor-in-a-kotlin-app-b70db1b5b8e5 */
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var pos: Boolean = false
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

        val anim = TranslateAnimation(0f, amountToMoveRight, 0f, amountToMoveDown)
        anim.duration = 2000

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,100)
                params.topMargin += amountToMoveDown.toInt()
                params.leftMargin += amountToMoveRight.toInt()
                view.layoutParams = params
            }
        })


        view.startAnimation(anim)


        /*
        val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            coordinateLayout.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 2500L

        valueAnimator.start()

        */

        /*
        if(Y < -2){
            val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
            coordinateLayout.startAnimation(animation)
            return
        }

        if(Y > 2){
            val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
            coordinateLayout.startAnimation(animation)
            return
        }
        /*
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        coordinateLayout.startAnimation(animation)
        */

        */


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

                    /*
                    Log.d("sensor","SensorX changed: ${event.values[0]}")
                    Log.d("sensor","SensorY changed: ${event.values[1]}")
                    Log.d("sensor","SensorZ changed: ${event.values[2]}")
*/
                    this.tvAccelerometerX.text = "X: ${event.values[0]}"
                    this.tvAccelerometerY.text = "Y: ${event.values[1]}"
                    this.tvAccelerometerZ.text = "Z: ${event.values[2]}"

                    animate(event.values[0], event.values[1], event.values[2], coordinateLayout)

                }

            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sensor-changed", "At sensor: $sensor, accuracy: $accuracy")
    }


}

