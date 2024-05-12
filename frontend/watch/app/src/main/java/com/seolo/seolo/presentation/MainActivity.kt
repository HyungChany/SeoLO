package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.MainChkFragment
import com.seolo.seolo.fragments.MainNFCFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()  // 액션바 숨기기

        setContentView(R.layout.activity_layout)  // 메인 레이아웃 설정

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)  // ViewPager2 인스턴스화
        val adapter = CarouselStateAdapter(this@MainActivity)  // Adapter 인스턴스화

        // 프래그먼트를 ViewPager2에 추가
        adapter.addFragment(MainChkFragment.newInstance("LOTO 잠금"))
        adapter.addFragment(MainNFCFragment.newInstance("NFC 인증"))
        adapter.addFragment(RedoPinNumberFragment.newInstance())

        viewPager.adapter = adapter
    }
}
