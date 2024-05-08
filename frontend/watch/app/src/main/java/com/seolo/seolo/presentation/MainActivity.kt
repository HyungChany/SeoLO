package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.seolo.seolo.databinding.ActivityMainBinding
import com.seolo.seolo.fragments.MainFragment

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2 설정
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2  // 페이지 수

        override fun createFragment(position: Int): Fragment {
            // 각 위치에 따라 다른 프래그먼트 인스턴스를 반환
            return MainFragment.newInstance(if (position == 0) "LOTO\n잠금" else "NFC 모드", position)
        }
    }
}
