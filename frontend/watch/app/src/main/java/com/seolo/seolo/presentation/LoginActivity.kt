package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.login_layout)
        val loginToken = "qwer1234"

        if (loginToken != " ") {
            // 로그인 성공 시 MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
        }
    }
}
