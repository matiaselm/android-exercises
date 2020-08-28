package com.example.exercises

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log


class MainActivity : AppCompatActivity() {
    private val myHandler: Handler = object :
            Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what== 0) {
                txt_network.text= inputMessage.obj.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Thread-lab","onCreate")
        if (isNetworkAvailable()) {
            val myRunnable = Conn(
                    myHandler)
            val myThread = Thread(myRunnable)
            myThread.start()
        }
    }

    // from here: http://chintanrathod.com/kotlin-check-internet-connection-available-android/
    private fun isNetworkAvailable(): Boolean {
        Log.d("Thread-lab","isNetworkAvailable")
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true }
}

