package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.CheckListFragment

class CheckListActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.checklist_layout)

        viewPager = findViewById(R.id.viewPagerChecklist)
        val adapter = CarouselStateAdapter(this)

        // 여러 체크리스트 프래그먼트를 추가
        adapter.addFragment(CheckListFragment.newInstance("Item 1"))
        adapter.addFragment(CheckListFragment.newInstance("Item 2"))
        adapter.addFragment(CheckListFragment.newInstance("Item 3"))

        viewPager.adapter = adapter
    }

    fun moveToNextPage() {
        val currentItem = viewPager.currentItem
        if (currentItem < (viewPager.adapter?.itemCount ?: (0 - 1))) {
            viewPager.currentItem = currentItem + 1
        }
    }
}
