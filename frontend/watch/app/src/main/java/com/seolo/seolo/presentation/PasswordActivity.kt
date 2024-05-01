
package com.seolo.seolo.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class PasswordActivity : AppCompatActivity() {
    private lateinit var passcodeView: TextView
    private lateinit var numberPicker: NumberPicker
    private val passcode = StringBuilder("----")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.password_layout) // 오타 수정

        passcodeView = findViewById(R.id.passcode_view)
        numberPicker = findViewById(R.id.number_picker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 9

        findViewById<Button>(R.id.button_confirm).setOnClickListener {
            val index = passcode.indexOf("-")
            if (index != -1) {
                passcode.setCharAt(index, '*')
                passcodeView.text = passcode.toString()
            }
        }
    }
}