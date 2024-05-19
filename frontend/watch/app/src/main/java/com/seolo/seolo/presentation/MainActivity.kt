package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.MainBluetoothFragment
import com.seolo.seolo.fragments.MainChkFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_layout)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@MainActivity)

        // 프래그먼트를 ViewPager2에 추가
        adapter.addFragment(MainChkFragment.newInstance("LOTO 잠금"))
        adapter.addFragment(MainBluetoothFragment.newInstance("Bluetooth"))
        adapter.addFragment(RedoPinNumberFragment.newInstance())

        viewPager.adapter = adapter
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
        // 뒤로가기 버튼 비활성화
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}
