package com.seolo.seolo.presentation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R

class LockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()

        setContentView(R.layout.lock_layout)

        // 잠금해제시 보이는 화면 코드. 조건문 추가 필요

        // ImageView 인스턴스를 찾아서 'img_unlock' 리소스로 이미지 변경
        // 'findViewById'는 레이아웃 파일에서 ID가 'imageViewLock'인 뷰를 찾아 ImageView로 캐스팅
        val imageViewUnLock: ImageView = findViewById(R.id.imageViewLock)
        imageViewUnLock.setImageResource(R.drawable.img_unlock)

        // TextView 인스턴스를 찾아서 'Unlock' 리소스 문자열로 텍스트 변경
        // 'findViewById'는 레이아웃 파일에서 ID가 'textViewLock'인 뷰를 찾아 TextView로 캐스팅
        val textViewLock : TextView = findViewById(R.id.textViewLock)
        textViewLock.text = getString(R.string.Unlock)
    }
}

