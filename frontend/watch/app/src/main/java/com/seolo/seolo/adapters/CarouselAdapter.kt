package com.seolo.seolo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}
