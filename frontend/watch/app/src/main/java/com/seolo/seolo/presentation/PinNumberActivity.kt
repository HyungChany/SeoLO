package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class PinNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.password_layout)
    }

    fun onConfirmButtonClick(view: View) {
        val pinNumberEditText = findViewById<EditText>(R.id.pinNumberEditText)
        val pinNumber = pinNumberEditText.text.toString()

        if (pinNumber == "1234") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "잘못된 핀 번호입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
