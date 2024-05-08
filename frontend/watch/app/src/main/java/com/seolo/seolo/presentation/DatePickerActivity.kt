package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

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

        // 확인 버튼 참조
        val confirmButton: Button = findViewById(R.id.confirm_button)

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
            // TimePickerActivity로 이동하는 인텐트 생성
            val intent = Intent(this, TimePickerActivity::class.java)
            // 액티비티 시작
            startActivity(intent)
        }
    }
}
