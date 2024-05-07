package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

// EquipmentActivity 클래스 정의
class EquipmentActivity : AppCompatActivity() {
    // 장비 목록 초기화
    private val equipments = listOf(" ", "장비1", "장비2", "장비3", "장비4", "장비5")

    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 액션바 숨기기
        supportActionBar?.hide()

        // 레이아웃 설정
        setContentView(R.layout.basic_wheel_picker_layout)

        // WheelPicker 초기화 및 어댑터 설정
        val equipmentPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val equipmentAdapter = WheelPickerAdapter(equipments)
        equipmentPicker.adapter = equipmentAdapter

        // WheelPicker를 중앙으로 스크롤
        equipmentPicker.post {
            val middlePosition = equipments.size / 2
            equipmentPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        // 확인 버튼 클릭 이벤트 처리
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            val intent = Intent(this, WorkActivity::class.java)
            startActivity(intent)
        }
    }
}
