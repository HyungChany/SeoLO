package com.seolo.seolo.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.R
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LastLOTOInfoFragment
import com.seolo.seolo.fragments.LOTOInfoFragment

// LOTOInfoActivity 클래스 정의
class LOTOInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.activity_layout)

        // ViewPager2와 CarouselStateAdapter 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@LOTOInfoActivity)

        // Fragment 추가
        adapter.addFragment(LOTOInfoFragment.newInstance("Title 1", "Content 1"))
        adapter.addFragment(LOTOInfoFragment.newInstance("Title 2", "Content 2"))
        adapter.addFragment(LOTOInfoFragment.newInstance("Title 3", "Content 3"))
        adapter.addFragment(LastLOTOInfoFragment.newInstance("Title 4", "Content 4"))

        // ViewPager2에 어댑터 설정
        viewPager.adapter = adapter

        // 페이지 변경 리스너 등록
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 페이지 변경 시 화살표 업데이트
                updateArrows(position)
            }
        })
    }

    // 화살표 업데이트 메서드
    private fun updateArrows(position: Int) {
        val leftArrow: ImageView = findViewById(R.id.slideLeftIcon)

        if (position == 0) {
            leftArrow.visibility = View.INVISIBLE
        } else {
            leftArrow.visibility = View.VISIBLE
        }
    }
}
