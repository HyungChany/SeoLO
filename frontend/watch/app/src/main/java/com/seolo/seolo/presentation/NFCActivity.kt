package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding
import com.seolo.seolo.databinding.NfcLayoutBinding

class NFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()

        // View Binding 초기화
        binding = NfcLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.main_sample3).into(binding.nfcView)

        // ImageView 오버레이 이미지 로딩
        Glide.with(this).load(R.drawable.img_nfc).into(binding.imageViewOverlay)

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
