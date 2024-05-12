package com.seolo.seolo.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LOTOInfoLastFragment
import com.seolo.seolo.fragments.LOTOInfoFragment
import com.seolo.seolo.helper.SessionManager

class LOTOInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.activity_layout)

        // ViewPager2와 CarouselStateAdapter 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@LOTOInfoActivity)

        // SessionManager에서 정보 받아오기
        val facilityName = SessionManager.selectedFacilityName
        val machineID = SessionManager.selectedMachineId
        val machineName = SessionManager.selectedMachineName
        val taskTemplateID = SessionManager.selectedTaskTemplateId
        val taskPrecaution = SessionManager.selectedTaskPrecaution
        val date = SessionManager.selectedDate + SessionManager.selectedTime
        val simpleDate =
            SessionManager.selectedSimpleDate + "\n" + SessionManager.selectedSimpleTime

        // Fragment 추가
        facilityName?.let { LOTOInfoFragment.newInstance("작업장", it) }
            ?.let { adapter.addFragment(it) }
        machineName?.let { LOTOInfoFragment.newInstance("장비", it) }?.let { adapter.addFragment(it) }
        taskPrecaution?.let { LOTOInfoFragment.newInstance("작업내용", it) }
            ?.let { adapter.addFragment(it) }
        adapter.addFragment(LOTOInfoLastFragment.newInstance("종료 예상 시간", simpleDate))

        // ViewPager2에 어댑터 설정
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
