package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import com.seolo.seolo.helper.TokenManager

// SplashActivity 클래스 정의
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린 설치
        val splashScreen = installSplashScreen()

        // 스플래시 레이아웃 설정
        setContentView(R.layout.splash_layout)

        // 스플래시 화면을 잠시 유지한 후 토큰 체크 함수 호출
        Handler(Looper.getMainLooper()).postDelayed({
            checkTokenAndNavigate()
        }, 2000)
    }

    // 토큰 체크 및 화면 전환
    private fun checkTokenAndNavigate() {
        if (TokenManager.getAccessToken(this).isNullOrEmpty()) {
            navigateToLoginActivity()
        } else {
            navigateToPinNumberActivity()
        }
    }

    // PIN 입력 액티비티로 이동
    private fun navigateToPinNumberActivity() {
        val intent = Intent(this, PinNumberActivity::class.java)
        startActivity(intent)
        finish()
    }

    // 로그인 액티비티로 이동
    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
