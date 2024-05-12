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
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
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
            val selectedMonth = datePicker.month
            val selectedDay = datePicker.day
            val dateString = "$selectedYear-${selectedMonth + 1}-$selectedDay-"
            val SimpleDateString = "$selectedYear. ${selectedMonth + 1}. $selectedDay"

            // SessionManager에 날짜 저장
            SessionManager.selectedDate = dateString
            SessionManager.selectedSimpleDate = SimpleDateString

            // TimePickerActivity로 이동하는 인텐트 생성
            val intent = Intent(this, TimePickerActivity::class.java)
            startActivity(intent)
        }
    }
}
