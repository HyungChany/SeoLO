package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

class LocationActivity : AppCompatActivity() {
    private val locations = listOf(" ","rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa", "rkskdkaskddsa"," ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.basic_wheel_picker_layout)


        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.location_view)
        val locationAdapter = WheelPickerAdapter(locations)
        locationPicker.adapter = locationAdapter

        locationPicker.post {
            locationPicker.layoutManager?.scrollToPosition(3)
        }
    }
}
