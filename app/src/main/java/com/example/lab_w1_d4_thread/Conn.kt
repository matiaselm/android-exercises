package com.example.lab_w1_d4_thread

import android.os.Handler
import android.util.Log
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Conn (
    mHand: Handler,
    val fname: String,
    val lname: String) : Runnable {

        private val myHandler = mHand

        override fun run() {
            Log.d("Coroutine-lab", "in run func")
            try {
                // val myUrl = URL("https://a.com/post.php")
                val myUrl = URL("https://www.w3.org/TR/PNG/iso_8859-1.txt")
                val myConn = myUrl.openConnection() as HttpURLConnection

                // myConn.requestMethod = "POST"
                myConn.requestMethod = "GET"
                myConn.doOutput = true

                /*
                val ostream = myConn.getOutputStream()

                ostream.bufferedWriter().use {
                    it.write("fn=${fname}&ln=${lname}")
                }
                */

                val istream: InputStream = myConn.getInputStream()
                val allText = istream.bufferedReader().use {
                    it.readText()
                }

                val result = StringBuilder()
                result.append(allText)
                val str = result.toString()

                val msg = myHandler.obtainMessage()
                msg.what = 0
                msg.obj = str
                myHandler.sendMessage(msg)

            }catch(e: Exception){
                Log.d("Coroutine-lab", "$e")
            }
        }
    }