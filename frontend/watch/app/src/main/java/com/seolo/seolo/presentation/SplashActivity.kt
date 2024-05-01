package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 시스템 스플래시 스크린 API 초기화
        installSplashScreen()

        // 사용자 정의 스플래시 레이아웃 설정
        setContentView(R.layout.splash_layout)

        // 일정 시간 후 MainActivity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // SplashActivity를 스택에서 제거
        }, 3000)  // 3000ms = 3초 동안 스플래시 화면 표시
    }
}
