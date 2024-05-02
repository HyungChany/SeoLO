package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding

class RealMainActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()

        // 뷰 바인딩 초기화
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .asGif()
            .load(R.drawable.main_sample0)
            .into(binding.mainView)
        binding.textViewOverlay.text = "NFC"

    }
}
