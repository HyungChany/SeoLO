package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import com.seolo.seolo.services.TokenManager

// SplashActivity 클래스 정의
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린 설치
        installSplashScreen()

        // 스플래시 레이아웃 설정
        setContentView(R.layout.splash_layout)

        // 스플래시 화면을 잠시 유지한 후 토큰 체크
        Handler(Looper.getMainLooper()).postDelayed({
            checkTokenAndNavigate()
        }, 2000)
    }

    private fun checkTokenAndNavigate() {
        if (TokenManager.getAccessToken(this).isNullOrEmpty()) {
            navigateToLoginActivity()
        } else {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
