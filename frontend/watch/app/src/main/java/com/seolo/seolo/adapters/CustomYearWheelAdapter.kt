
package com.seolo.seolo.adapters

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.seolo.seolo.R
import sh.tyy.wheelpicker.core.ItemEnableWheelAdapter
import sh.tyy.wheelpicker.core.TextWheelViewHolder
import java.lang.ref.WeakReference
import sh.tyy.wheelpicker.core.*
class CustomYearWheelAdapter(
    valueEnabledProvider: WeakReference<ValueEnabledProvider>
) : ItemEnableWheelAdapter(valueEnabledProvider) {

    private val baseYear = 2000

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override val valueCount: Int
        get() = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextWheelViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.date_picker_layout, parent, false) as TextView
        return TextWheelViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextWheelViewHolder, position: Int) {
        val year = baseYear + position
        val text = SpannableString("$year")
        val isEnabled = valueEnabledProvider.get()?.isEnabled(this, position) ?: true
        holder.onBindData(
            TextWheelPickerView.Item(
                id = "$year",
                text = text,
                isEnabled = isEnabled
            )
        )
    }
}
