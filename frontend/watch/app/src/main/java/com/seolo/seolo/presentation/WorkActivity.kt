package com.seolo.seolo.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LastWorksFragment
import com.seolo.seolo.fragments.WorksFragment

class WorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // 메인 레이아웃 설정
        setContentView(R.layout.activity_layout)

        // ViewPager2와 CarouselStateAdapter 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@WorkActivity)

        // CardFragment 인스턴스 생성 및 이미지 리소스 지정
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_maintenance, "정비"))
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_clean, "청소"))
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_repair, "수리"))
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_etc, "기타"))
        adapter.addFragment(LastWorksFragment.newInstance(R.drawable.img_mic, "음성"))

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
