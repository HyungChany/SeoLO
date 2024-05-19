package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class LockCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // 레이아웃 설정
        setContentView(R.layout.lock_layout)

        val imageViewLock: ImageView = findViewById(R.id.imageViewLock)
        imageViewLock.setImageResource(R.drawable.img_lock)

        // TextView 인스턴스 찾아서 텍스트 설정
        val textViewLock: TextView = findViewById(R.id.textViewLock)
        textViewLock.text = getString(R.string.Lock)


        // 뒤로가기 버튼 비활성화
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        // 3초 후 MainActivity로 전환
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
