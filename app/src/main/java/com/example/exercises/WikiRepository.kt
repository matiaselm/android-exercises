package com.example.exercises

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object DemoApi {
    const val URL = "https://en.wikipedia.org/w/api.php"

    interface Service {
        @GET("api.php")
        suspend fun president(@Query("srsearch") action: String): President
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Service::class.java)!!
}

class WikiRepository() {
    private val call = DemoApi.service

    suspend fun getWiki(searchTerm: String) = call.president(searchTerm)
}