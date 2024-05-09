package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.ChecklistFragment

// CheckListActivity 클래스 정의
class ChecklistActivity : AppCompatActivity() {
    // ViewPager2 선언
    private lateinit var viewPager: ViewPager2

    // Activity가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.checklist_layout)

        // ViewPager2 초기화 및 어댑터 설정
        viewPager = findViewById(R.id.viewPagerChecklist)
        val adapter = CarouselStateAdapter(this)

        // 체크리스트 프래그먼트 추가
        adapter.addFragment(ChecklistFragment.newInstance("Item 1"))
        adapter.addFragment(ChecklistFragment.newInstance("Item 2"))
        adapter.addFragment(ChecklistFragment.newInstance("Item 3"))

        // ViewPager2에 어댑터 설정
        viewPager.adapter = adapter
    }

    // 다음 페이지로 이동하는 메서드
    fun moveToNextPage() {
        val currentItem = viewPager.currentItem
        val totalItems = viewPager.adapter?.itemCount ?: 0
        if (currentItem < totalItems - 1) {
            viewPager.currentItem = currentItem + 1
        } else {
            // 마지막 프래그먼트에서 LocationActivity로 전환
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
