package com.seolo.seolo.presentation

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import com.seolo.seolo.databinding.NfcLayoutBinding
import pl.droidsonroids.gif.GifDrawable

// NFCActivity 클래스 정의
class NFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 스플래시 스크린 설치
        installSplashScreen()
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // View Binding 초기화
        binding = NfcLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // GIF 이미지 설정 및 속도 조절
        val gifFromResource = GifDrawable(resources, R.drawable.main_sample3)
        gifFromResource.setSpeed(2.0f)
        binding.nfcView.setImageDrawable(gifFromResource)

        // ImageView에 일반 이미지 로딩
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.img_nfc)
        binding.imageViewOverlay.setImageDrawable(drawable)

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
