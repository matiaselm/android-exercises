package com.example.exercises

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.custom_list_item.view.*

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
        rowView.title.text = president.name
        rowView.description.text = "${president.startDuty} - ${president.endDuty}"

        return rowView
    }
}