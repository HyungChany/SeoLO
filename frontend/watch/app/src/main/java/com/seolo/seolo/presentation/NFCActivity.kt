package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import com.seolo.seolo.databinding.NfcLayoutBinding
import pl.droidsonroids.gif.GifDrawable

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

        val gifFromResource = GifDrawable(resources, R.drawable.main_sample3)
        gifFromResource.setSpeed(2.0f)

        binding.nfcView.setImageDrawable(gifFromResource)

//        // ImageView 오버레이 이미지 로딩
//        Glide.with(this).load(R.drawable.img_nfc).into(binding.imageViewOverlay)

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
