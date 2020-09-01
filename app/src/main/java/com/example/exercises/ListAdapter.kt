package com.example.exercises

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListAdapter(private val context: Context, private val dataSource: Array<President>)
    : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val president = getItem(position) as President

        // Get view for row item
        val rowView = inflater.inflate(R.layout.custom_list_item, parent, false)
        val titleView = rowView.findViewById(R.id.title) as TextView
        val descriptionView = rowView.findViewById(R.id.description) as TextView

        titleView.text = president.name
        descriptionView.text = "${president.startDuty} - ${president.endDuty}"

        return rowView
    }
}