package com.seolo.seolo.presentation

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.NfcLayoutBinding

// NFCActivity 클래스 정의
class NFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // View Binding 초기화
        binding = NfcLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.main_sample6).into(binding.nfcView)

        // ImageView에 일반 이미지 로딩
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.img_nfc)
        binding.imageViewOverlay.setImageDrawable(drawable)

        // 화면 터치 리스너 설정
        binding.root.setOnClickListener {
            // CheckListActivity로 이동하는 Intent 생성
            val intent = Intent(this, ChecklistActivity::class.java)
            startActivity(intent)
        }
    }
}
