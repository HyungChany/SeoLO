package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.helper.SessionManager
import sh.tyy.wheelpicker.DatePickerView
import java.util.Calendar

// DatePickerActivity 클래스 정의
class DatePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.date_picker_layout)

        val confirmButton: Button = findViewById(R.id.confirm_button)
        val datePicker: DatePickerView = findViewById(R.id.date_picker_view)

        // 현재 날짜 설정
        val now = Calendar.getInstance()
        datePicker.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
            // 선택된 날짜를 문자열로 변환하여 저장
            val selectedYear = datePicker.year
            val selectedMonth = datePicker.month + 1
            val selectedDay = datePicker.day

            // 날짜를 yyyy-MM-dd 형식으로 포맷
            val dateString = String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDay)
            val simpleDateString = String.format("%04d. %02d. %02d", selectedYear, selectedMonth, selectedDay)

            // SessionManager에 날짜 저장
            SessionManager.selectedDate = dateString
            SessionManager.selectedSimpleDate = simpleDateString

            // TimePickerActivity로 이동하는 인텐트 생성
            val intent = Intent(this, TimePickerActivity::class.java)
            startActivity(intent)
        }
    }
}
