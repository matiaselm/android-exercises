package com.example.exercises

import android.util.Log

object GlobalModel {
    val presidents: kotlin.collections.MutableList<President> = java.util.ArrayList()

    init{
        Log.d("USR", "This ($this) is a singleton")

        // construct the data source
        presidents.add(President("Kaarlo Stahlberg",1919,1925, "First president"))
        presidents.add(President("Lauri Relander",1925,1931, "Second president"))
        presidents.add(President("P. E. Svinhufvud",1931,1937, "Third president"))
        presidents.add(President("Kyösti Kallio",1937,1940, "Fourth president"))
        presidents.add(President("Risto Ryti",1940,1944, "Fifth president"))
        presidents.add(President("Carl Gustaf Emil Mannerheim",1944,1946, "Sixth president"))
        presidents.add(President("Juho Kusti Paasikivi",1946,1956, "Seventh president"))
        presidents.add(President("Urho Kekkonen",1956,1982, "Eight president"))
        presidents.add(President("Mauno Koivisto",1982,1994, "Ninth president"))
        presidents.add(President("Martti Ahtisaari",1994,2000, "Tenth president"))
        presidents.add(President("Tarja Halonen",2000,2012, "Eleventh president"))
        presidents.add(President("Sauli Niinistö",2012,2024, "Twelfth president"))
    }
}