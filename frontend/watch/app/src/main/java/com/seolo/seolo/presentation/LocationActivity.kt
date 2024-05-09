package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import com.seolo.seolo.model.FacilityItem
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

// LocationActivity 클래스 정의
class LocationActivity : AppCompatActivity() {
    private lateinit var facilities: List<FacilityItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.basic_wheel_picker_layout)

        // 인텐트에서 facilities 정보를 Parcelable로 받기
        facilities = intent.getParcelableArrayListExtra<FacilityItem>("facilities") ?: arrayListOf()
        val facilityNames = listOf(" ") + facilities.map { it.name } + " "


        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val locationAdapter = WheelPickerAdapter(facilityNames)
        locationPicker.adapter = locationAdapter

        locationPicker.post {
            val middlePosition = facilityNames.size / 2
            locationPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            val intent = Intent(this, EquipmentActivity::class.java)
            startActivity(intent)
        }
    }
}
