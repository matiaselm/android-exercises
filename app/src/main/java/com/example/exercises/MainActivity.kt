package com.example.exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  This creates an array of strings that’ll contain the text to be displayed in the ListView.
        val presidentList = GlobalModel.presidents
        val listItems = arrayOfNulls<String>(presidentList.size)

        //  This populates the ListView’s data source with the presidents loaded before.
        for (i in 0 until presidentList.size) {
            val president = presidentList[i]
            listItems[i] = president.toString()
        }

        /*  This creates and sets a simple adapter for the ListView.
            The ArrayAdapter takes in the current context,
            a layout file specifying what each row in the list should look like,
            and the data that will populate the list as arguments.*/
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        president_list_view.adapter = adapter

        }
    }

