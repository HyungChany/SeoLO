package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R

class DatePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.date_picker_layout)

    }
}

