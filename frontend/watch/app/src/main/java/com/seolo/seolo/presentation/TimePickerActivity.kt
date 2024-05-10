package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.helper.SessionManager
import sh.tyy.wheelpicker.WeekdayTimePickerView
import java.util.Calendar
import java.util.Locale

class TimePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 기본 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)

        // 액션바 숨기기
        supportActionBar?.hide()

        // XML 레이아웃 설정
        setContentView(R.layout.time_picker_layout)

        val confirmButton: Button = findViewById(R.id.confirm_button)
        val timePicker: WeekdayTimePickerView = findViewById(R.id.time_picker_view)

        // 현재 시간 설정
        val now = Calendar.getInstance()
        timePicker.weekday = now.get(Calendar.DAY_OF_WEEK)
        timePicker.hour = now.get(Calendar.HOUR_OF_DAY)
        timePicker.minute = now.get(Calendar.MINUTE)

        confirmButton.setOnClickListener {
            // 선택된 시간을 문자열로 변환하여 저장
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            val timeString = String.format(Locale.ROOT,"T%02d:%02d:00", selectedHour, selectedMinute)
            val simpleTimeString = String.format(Locale.ROOT,"%02d:%02d", selectedHour, selectedMinute)

            // SessionManager에 시간 저장
            SessionManager.selectedTime = timeString
            SessionManager.selectedSimpleTime = simpleTimeString

            // LOTOInfoActivity로 이동하는 인텐트 생성
            val intent = Intent(this, LOTOInfoActivity::class.java)
            startActivity(intent)
        }
    }
}
