package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LoginFragment
import com.seolo.seolo.fragments.LoginPartTwoFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    var companyCode: String = ""
    var employeeNumber: String = ""
    var password: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        viewPager = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this)
        adapter.addFragment(LoginFragment.newInstance("회사코드를 입력하세요."))
        adapter.addFragment(LoginFragment.newInstance("사번을 입력하세요."))
        adapter.addFragment(LoginPartTwoFragment())
        viewPager.adapter = adapter
    }

    fun nextPage() {
        val currentItem = viewPager.currentItem
        if (currentItem < (viewPager.adapter?.itemCount ?: 0)) {
            viewPager.setCurrentItem(currentItem + 1, true)
        }
    }
}
