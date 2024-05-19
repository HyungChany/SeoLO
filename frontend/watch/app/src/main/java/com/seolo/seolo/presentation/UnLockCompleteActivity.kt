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

class UnLockCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // 레이아웃 설정
        setContentView(R.layout.lock_layout)

        val imageViewUnLock: ImageView = findViewById(R.id.imageViewLock)
        imageViewUnLock.setImageResource(R.drawable.img_unlock)

        val textViewLock: TextView = findViewById(R.id.textViewLock)
        textViewLock.text = getString(R.string.Unlock)

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
