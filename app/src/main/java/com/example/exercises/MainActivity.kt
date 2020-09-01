package com.example.exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  This creates an array of presidents that’ll contain the text to be displayed in the ListView.
        val presidentList = GlobalModel.presidents.toTypedArray()

        val adapter = ListAdapter(this, presidentList)
        presidentListView.adapter = adapter

        presidentListView.setOnItemClickListener { _, _, position, _ ->
            Log.d("list-lab", "selected $position")
            presidentNameView.text = presidentList[position].name
            presidentDescriptionView.text = presidentList[position].description

        }

        presidentListView.setOnItemLongClickListener { _, _, position, _ ->
            Log.d("list-lab","long click on $position")

            true
        }
    }
}

