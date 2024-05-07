package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

// LoginActivity 클래스 정의
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.login_layout)

        // 예시 로그인 토큰
        val loginToken = "qwer1234"

        // 로그인 토큰이 존재하는 경우 MainActivity로 이동
        if (loginToken != " ") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // 현재 LoginActivity 종료
            finish()
        } else {
            // 로그인 토큰이 존재하지 않는 경우 아무 작업도 하지 않음
        }
    }
}
