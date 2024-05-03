package com.seolo.seolo.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R

class WheelPickerAdapter(private val selects: List<String>) :
    RecyclerView.Adapter<WheelPickerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1) as TextView

        init {
            textView.gravity = Gravity.CENTER
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = selects[position].toString()
        holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.SNOW))
    }

    override fun getItemCount() = selects.size
}
