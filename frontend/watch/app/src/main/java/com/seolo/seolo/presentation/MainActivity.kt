package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.main_sample6).into(binding.gifImageView)

        // 오버레이 텍스트 설정
        binding.textViewOverlay.text = "LOTO\n잠금"

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
