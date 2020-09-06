package com.example.exercises


import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WikiApiService {
    @GET("api.php")
    // suspend fun userName(@Query("name") action: String): Model.Employee
    fun hitCountCheck(
        @Query("action") action: String,
        @Query("format") format: String,
        @Query("list") list: String,
        @Query("srsearch") srsearch: String
    ): Observable<Model.Result>

    companion object {
        fun create(): WikiApiService {
            val URL = "https://en.wikipedia.org/w/"

            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WikiApiService::class.java)
        }
    }
}