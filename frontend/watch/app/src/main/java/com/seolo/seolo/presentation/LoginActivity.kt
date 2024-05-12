package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LoginPartOneFragment
import com.seolo.seolo.fragments.LoginPartTwoFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    // 필드 초기화
    var companyCode: String = ""
    var username: String = ""
    var password: String = ""

    // 액티비티 생성 시 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        viewPager = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this)
        // 로그인 프래그먼트 및 두 번째 로그인 프래그먼트 추가
        adapter.addFragment(LoginPartOneFragment.newInstance("회사코드를 입력하세요."))
        adapter.addFragment(LoginPartOneFragment.newInstance("사번을 입력하세요."))
        adapter.addFragment(LoginPartTwoFragment())
        viewPager.adapter = adapter
    }

    // 다음 페이지로 이동하는 메서드
    fun nextPage() {
        val currentItem = viewPager.currentItem
        // 현재 페이지가 마지막 페이지가 아니면 다음 페이지로 이동
        if (currentItem < (viewPager.adapter?.itemCount ?: 0)) {
            viewPager.setCurrentItem(currentItem + 1, true)
        }
    }
}
