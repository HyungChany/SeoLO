package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pl.droidsonroids.gif.GifDrawable
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding

// MainActivity 클래스 정의
class MainActivity : ComponentActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 스플래시 스크린 설치
        installSplashScreen()

        // 뷰 바인딩 초기화
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // GIF 이미지 설정 및 속도 조절
        val gifFromResource = GifDrawable(resources, R.drawable.main_sample3)
        gifFromResource.setSpeed(2.0f)
        binding.gifImageView.setImageDrawable(gifFromResource)

        // 텍스트 설정
        binding.textViewOverlay.text = "LOTO\n잠금"

        // 레이아웃 클릭 시 CheckListActivity로 이동
        binding.root.setOnClickListener {
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
