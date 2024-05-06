package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

class EquipmentActivity : AppCompatActivity() {
    private val equipments = listOf(" ", "장비1", "장비2", "장비3", "장비4", "장비5")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.basic_wheel_picker_layout)


        val equipmentPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val equipmentAdapter = WheelPickerAdapter(equipments)
        equipmentPicker.adapter = equipmentAdapter

        equipmentPicker.post {
            val middlePosition = equipments.size / 2
            equipmentPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            val intent = Intent(this, WorkActivity::class.java)
            startActivity(intent)
        }
    }
}
