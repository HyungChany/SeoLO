package com.seolo.seolo.presentation

import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R

class CheckListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.checklist_layout)

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val textBox = findViewById<LinearLayout>(R.id.textbox)

        // textBox 클릭 시 checkBox의 체크 상태를 토글합니다.
        textBox.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }
    }
}
