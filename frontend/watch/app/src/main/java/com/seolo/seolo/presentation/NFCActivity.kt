package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding

class NFCActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()

        // View Binding 초기화
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.main_sample3).into(binding.mainView)

        // 오버레이 텍스트 설정
        binding.textViewOverlay.text = "NFC\n통신중..."

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
