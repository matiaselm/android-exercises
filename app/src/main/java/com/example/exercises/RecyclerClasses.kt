package com.example.exercises

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*


class RecyclerAdapter(var c: Context, var devices: ArrayList<Device>) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val device = devices[position]
        holder.nameTxt.text = device.name
        holder.macTxt.text = device.mac
        holder.lossTxt.text = device.signal

        holder.setItemClickListener(object : RecyclerHolder.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val currentDevice = devices[pos]

                Log.d("bluetooth-lab", "selected device: $currentDevice")
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, null)
        return RecyclerHolder(v)
    }

    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var myItemClickListener: ItemClickListener

        var nameTxt: TextView = itemView.deviceNameView
        var macTxt: TextView = itemView.deviceMacView
        var lossTxt: TextView = itemView.deviceSignalView

        internal fun setItemClickListener(ic: ItemClickListener) {
            this.myItemClickListener = ic
        }

        override fun onClick(v: View) {
            this.myItemClickListener.onItemClick(v, layoutPosition)
        }

        internal interface ItemClickListener {
            fun onItemClick(v: View, pos: Int)
        }
    }


}