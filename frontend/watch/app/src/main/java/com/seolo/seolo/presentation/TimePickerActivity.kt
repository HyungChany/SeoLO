package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import sh.tyy.wheelpicker.WeekdayTimePickerView
import java.util.Calendar

class TimePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.time_picker_layout)

        val confirmButton: Button = findViewById(R.id.confirm_button)
        val timePicker: WeekdayTimePickerView = findViewById(R.id.date_picker_view)

        // 현재 시간 설정
        val now = Calendar.getInstance()
        timePicker.weekday = now.get(Calendar.DAY_OF_WEEK)
        timePicker.hour = now.get(Calendar.HOUR_OF_DAY)
        timePicker.minute = now.get(Calendar.MINUTE)

        confirmButton.setOnClickListener {
            val intent = Intent(this, LOTOInfoActivity::class.java)
            startActivity(intent)
        }
    }
}
