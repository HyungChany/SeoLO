package com.seolo.seolo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    // Fragment 리스트를 저장하는 변수 선언
    private val fragments: MutableList<Fragment> = mutableListOf()

    // Fragment를 리스트에 추가하는 메서드
    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    // 지정된 위치의 Fragment를 생성하여 반환하는 메서드
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    // Fragment의 총 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return fragments.size
    }
}
