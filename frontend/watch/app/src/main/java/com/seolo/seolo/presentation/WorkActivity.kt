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
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.TaskResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // 메인 레이아웃 설정
        setContentView(R.layout.activity_layout)

        // ViewPager2와 CarouselStateAdapter 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = CarouselStateAdapter(this@WorkActivity)

        getTasks()

        // CardFragment 인스턴스 생성 및 이미지 리소스 지정
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_maintenance, "정비"))
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_clean, "청소"))
        adapter.addFragment(WorksFragment.newInstance(R.drawable.img_repair, "수리"))
        adapter.addFragment(LastWorksFragment.newInstance(R.drawable.img_etc, "기타"))

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

    // 작업 템플릿 요청
    private fun getTasks() {
        val token = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val service = RetrofitClient.taskService

        if (token != null && companyCode != null) {
            service.getTasks("Bearer $token", companyCode).enqueue(object : Callback<TaskResponse> {
                override fun onResponse(
                    call: Call<TaskResponse>, response: Response<TaskResponse>
                ) {
                    if (response.isSuccessful) {
                        val tasks = response.body()?.tasks ?: listOf()
                        Log.d("TaskResponse", "Tasks: $tasks")
                        Log.d("TaskResponse", "response.body: $response")
                    } else {
                        Log.e("TaskError", "Failed to get tasks: ${response.errorBody()}")
                    }

                }

                override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                    Log.e("MachineError", "Network error: ${t.message}")
                }
            })
        }

    }
}
