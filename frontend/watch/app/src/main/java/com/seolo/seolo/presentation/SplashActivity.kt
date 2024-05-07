package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R

// SplashActivity 클래스 정의
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린 설치
        installSplashScreen()

        // 스플래시 레이아웃 설정
        setContentView(R.layout.splash_layout)

        // 일정 시간 후 MainActivity로 이동하는 핸들러 설정
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // SplashActivity 종료
        }, 3000)  // 3초 후 이동
    }
}
