package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

// LocationActivity 클래스 정의
class LocationActivity : AppCompatActivity() {
    // 위치 목록 초기화
    private val locations = listOf(
        " ", "1공장", "2공장", "3공장", "4공장", "5공장", "6공장", "7공장", "8공장", " "
    )

    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.basic_wheel_picker_layout)

        // WheelPicker 초기화 및 어댑터 설정
        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val locationAdapter = WheelPickerAdapter(locations)
        locationPicker.adapter = locationAdapter

        // WheelPicker를 중앙으로 스크롤
        locationPicker.post {
            val middlePosition = locations.size / 2
            locationPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        // 확인 버튼 클릭 이벤트 처리
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            val intent = Intent(this, EquipmentActivity::class.java)
            startActivity(intent)
        }
    }
}
