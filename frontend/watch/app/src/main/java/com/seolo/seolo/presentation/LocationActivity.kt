package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

class LocationActivity : AppCompatActivity() {
    private val locations = listOf(
        " ", "1공장", "2공장", "3공장", "4공장", "5공장", "6공장", "7공장", "8공장", " "
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.basic_wheel_picker_layout)

        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val locationAdapter = WheelPickerAdapter(locations)
        locationPicker.adapter = locationAdapter

        locationPicker.post {
            val middlePosition = locations.size / 2
            locationPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            val intent = Intent(this, EquipmentActivity::class.java)
            startActivity(intent)
        }
    }
}
