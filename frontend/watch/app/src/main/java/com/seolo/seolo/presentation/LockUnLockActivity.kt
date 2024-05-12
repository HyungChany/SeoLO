package com.seolo.seolo.presentation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class LockUnLockActivity : AppCompatActivity() {
    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // 레이아웃 설정
        setContentView(R.layout.lock_layout)

        // 잠금 해제 화면 표시 코드
        // TODO: 잠금 해제 조건에 따라서 보여주는 화면을 구성
        // ImageView 인스턴스 찾아서 Unlock이미지 설정
        val imageViewUnLock: ImageView = findViewById(R.id.imageViewLock)
        imageViewUnLock.setImageResource(R.drawable.img_unlock)

        // TextView 인스턴스 찾아서 텍스트 설정
        val textViewLock: TextView = findViewById(R.id.textViewLock)
        textViewLock.text = getString(R.string.Unlock)
    }
}
