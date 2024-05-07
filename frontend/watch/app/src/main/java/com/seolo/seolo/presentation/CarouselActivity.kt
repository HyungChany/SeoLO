package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.CardFragment

// CarouselActivity 클래스 정의
class CarouselActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 스플래시 스크린 설치
        installSplashScreen()
        // 기본 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // 메인 레이아웃 설정
        setContentView(R.layout.activity_layout)

        // ViewPager2와 CarouselStateAdapter 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@CarouselActivity)

        // CardFragment 인스턴스 생성 및 어댑터에 추가
        adapter.addFragment(CardFragment())
        adapter.addFragment(CardFragment())
        adapter.addFragment(CardFragment())

        // ViewPager2에 어댑터 설정
        viewPager.adapter = adapter
    }
}
