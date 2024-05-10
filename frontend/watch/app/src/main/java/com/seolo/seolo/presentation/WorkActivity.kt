package com.seolo.seolo.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.LastWorksFragment
import com.seolo.seolo.fragments.WorksFragment
import com.seolo.seolo.model.TaskItem

class WorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_layout)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this)

        // Tasks 리스트 받기
        val tasks = intent.getParcelableArrayListExtra<TaskItem>("tasks")
        Log.d("WorkActivity", "tasks: $tasks")

        tasks?.let {
            for ((index, task) in it.withIndex()) {
                if (index == it.size - 1) {
                    val lastFragment = LastWorksFragment.newInstance(
                        getDrawableId(task.taskTemplateName),
                        task.taskTemplateId,
                        task.taskTemplateName,
                        task.taskPrecaution
                    )
                    adapter.addFragment(lastFragment)
                } else {
                    val fragment = WorksFragment.newInstance(
                        getDrawableId(task.taskTemplateName),
                        task.taskTemplateId,
                        task.taskTemplateName,
                        task.taskPrecaution
                    )
                    adapter.addFragment(fragment)
                }
            }
        }

        viewPager.adapter = adapter

        // 페이지 변경 리스너 등록
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateArrows(position)
            }
        })
    }

    private fun updateArrows(position: Int) {
        val leftArrow: ImageView = findViewById(R.id.slideLeftIcon)
        leftArrow.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
    }

    private fun getDrawableId(taskName: String): Int {
        return when (taskName) {
            "정비" -> R.drawable.img_maintenance
            "청소" -> R.drawable.img_clean
            "수리" -> R.drawable.img_repair
            else -> R.drawable.img_etc  // 기타 작업에 대한 기본 이미지
        }
    }
}
