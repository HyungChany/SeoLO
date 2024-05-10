package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.adapters.CarouselStateAdapter
import com.seolo.seolo.fragments.ChecklistFragment
import com.seolo.seolo.helper.ChecklistManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.FacilityItem
import com.seolo.seolo.model.FacilityResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChecklistActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var facilities: List<FacilityItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.checklist_layout)

        // ViewPager2와 어댑터 설정
        viewPager = findViewById(R.id.viewPagerChecklist)
        val adapter = CarouselStateAdapter(this)

        // 저장된 체크리스트 항목 불러오기
        val checklistItems = ChecklistManager.getChecklist(this)

        // 체크리스트 항목이 있으면 프래그먼트 추가, 없으면 기본 항목 추가
        if (!checklistItems.isNullOrEmpty()) {
            for (item in checklistItems) {
                adapter.addFragment(ChecklistFragment.newInstance(item.context))
            }
        } else {
            // 저장된 체크리스트가 없는 경우 기본 항목 추가
            adapter.addFragment(ChecklistFragment.newInstance("체크리스트 항목 없음"))
        }

        viewPager.adapter = adapter

        getFacilities()
    }

    fun moveToNextPage() {
        val currentItem = viewPager.currentItem
        val totalItems = viewPager.adapter?.itemCount ?: 0
        if (currentItem < totalItems - 1) {
            viewPager.currentItem = currentItem + 1
        } else {
            val intent = Intent(this, LocationActivity::class.java)
            intent.putExtra("facilities", ArrayList(facilities))
            startActivity(intent)
        }
    }

    private fun getFacilities() {
        val accessToken = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val username = TokenManager.getUserName(this)
        if (accessToken != null && companyCode != null && username != null) {
            RetrofitClient.facilityService.getFacilities(
                "Bearer $accessToken", companyCode, username
            ).enqueue(object : Callback<FacilityResponse> {
                override fun onResponse(
                    call: Call<FacilityResponse>, response: Response<FacilityResponse>
                ) {
                    if (response.isSuccessful) {
                        facilities = response.body()?.facilities ?: emptyList()
                        response.body()?.facilities?.forEach {
                            Log.d("Facility", "ID: ${it.id}, Name: ${it.name}")
                        }
                    } else {
                        Log.e(
                            "FacilityError",
                            "Error fetching facilities: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<FacilityResponse>, t: Throwable) {
                    Log.e("FacilityError", "Network error: ${t.message}")
                }
            })
        }
    }
}
