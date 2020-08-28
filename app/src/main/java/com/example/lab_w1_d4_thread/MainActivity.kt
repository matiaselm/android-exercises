package com.example.lab_w1_d4_thread

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    // Image link: https://i.redd.it/qs0v7vt9b7i51.jpg

    private val myHandler: Handler = object :
            Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what== 0) {
                txt_network.text= inputMessage.obj.toString()
            }
        }
    }

    // Loads an image from the URL and converts it to Bitmap, returns bitmap or null
    private suspend fun loadBitmap(imageUrl: URL): Bitmap? =
        withContext(Dispatchers.IO){
        try {
            val myConn = imageUrl.openConnection() as HttpURLConnection

            myConn.requestMethod = "GET"

            val inputStream: InputStream = myConn.inputStream

            return@withContext BitmapFactory.decodeStream(inputStream)
        }catch(e: Exception){
            Log.d("Coroutine-lab", "$e")
            return@withContext null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Coroutine-lab","onCreate")

        if (isNetworkAvailable()) {
            val imgUrl = URL("https://i.redd.it/qs0v7vt9b7i51.jpg")

            lifecycleScope.launch(Dispatchers.Main){
                val imgBitmap = loadBitmap(imgUrl)
                imageView.adjustViewBounds
                imageView.setImageBitmap(imgBitmap)
            }

            val myRunnable = Conn(
                    myHandler,
                    "John",
                    "Doe")
            val myThread = Thread(myRunnable)
            myThread.start()
        }
    }

    // from here: http://chintanrathod.com/kotlin-check-internet-connection-available-android/
    private fun isNetworkAvailable(): Boolean {
        Log.d("Coroutine-lab","isNetworkAvailable")
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true }
}

