package com.seolo.seolo.presentation

import DataLayerClient
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R

// SplashActivity 클래스 정의
class SplashActivity : ComponentActivity() {
    private lateinit var dataLayerClient: DataLayerClient
    private var tokenReceived = false  // 토큰 수신 상태 플래그

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린 설치
        installSplashScreen()

        // 스플래시 레이아웃 설정
        setContentView(R.layout.splash_layout)

        // DataLayerClient 인스턴스화
        dataLayerClient = DataLayerClient.getInstance(applicationContext)
        // 리스너 설정 및 토큰 수신 대기
        dataLayerClient.setListener(object : DataLayerClient.TokenReceiveListener {
            override fun onTokenReceived(token: String?) {
                // 토큰 수신 후 로직 처리
                if (token != null && token.isNotEmpty()) {
                    tokenReceived = true
                    navigateToMainActivity()
                }
            }
        })

        // 5초 후 토큰 수신 실패 시 로그인 액티비티로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            if (!tokenReceived) {  // 토큰이 수신되지 않았을 경우
                navigateToLoginActivity()
            }
        }, 3000)  // 3초 지연
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

    override fun onDestroy() {
        super.onDestroy()
        dataLayerClient.cleanup()  // 리스너 제거
    }
}
