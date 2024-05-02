package com.seolo.seolo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R

class LocationAdapter(private val locations: List<String>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = locations[position]
        holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context, com.google.android.material.R.color.m3_ref_palette_white))
    }

    override fun getItemCount() = locations.size
}
