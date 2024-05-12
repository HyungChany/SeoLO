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
    // 뷰페이저 및 시설물 목록 초기화
    private lateinit var viewPager: ViewPager2
    private var facilities: List<FacilityItem> = emptyList()

    // 액티비티 생성 시 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 기본 테마 설정 및 액션바 숨김
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.checklist_layout)

        // 뷰페이저 초기화
        viewPager = findViewById(R.id.viewPagerChecklist)
        val adapter = CarouselStateAdapter(this)

        // 체크리스트 아이템 가져오기
        val checklistItems = ChecklistManager.getChecklist(this)

        // 체크리스트가 비어있지 않으면 각 아이템을 프래그먼트로 추가
        if (!checklistItems.isNullOrEmpty()) {
            for (item in checklistItems) {
                adapter.addFragment(ChecklistFragment.newInstance(item.context))
            }
        } else {
            // 체크리스트가 비어있을 경우, "체크리스트 항목 없음" 프래그먼트 추가
            adapter.addFragment(ChecklistFragment.newInstance("체크리스트 항목 없음"))
        }

        // 어댑터 설정
        viewPager.adapter = adapter

        // 시설물 정보 가져오기
        getFacilities()
    }

    // 다음 페이지로 이동하는 메서드
    fun moveToNextPage() {
        val currentItem = viewPager.currentItem
        val totalItems = viewPager.adapter?.itemCount ?: 0
        if (currentItem < totalItems - 1) {
            viewPager.currentItem = currentItem + 1
        } else {
            // 시설물 정보를 담아 LocationActivity로 이동
            val intent = Intent(this, FacilityActivity::class.java)
            intent.putExtra("facilities", ArrayList(facilities))
            startActivity(intent)
        }
    }

    // 시설물 정보를 가져오는 메서드
    private fun getFacilities() {
        val accessToken = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val username = TokenManager.getUserName(this)
        if (accessToken != null && companyCode != null && username != null) {
            // Retrofit을 사용하여 서버로부터 시설물 정보 요청
            RetrofitClient.facilityService.getFacilities(
                "Bearer $accessToken", companyCode, username
            ).enqueue(object : Callback<FacilityResponse> {
                override fun onResponse(
                    call: Call<FacilityResponse>, response: Response<FacilityResponse>
                ) {
                    if (response.isSuccessful) {
                        // 성공적으로 응답을 받았을 때 처리
                        facilities = response.body()?.facilities ?: emptyList()
                        response.body()?.facilities?.forEach {
                            Log.d("Facility", "ID: ${it.id}, Name: ${it.name}")
                        }
                    } else {
                        // 응답이 실패했을 때 에러 처리
                        Log.e(
                            "FacilityError",
                            "Error fetching facilities: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<FacilityResponse>, t: Throwable) {
                    // 네트워크 오류 발생 시 에러 처리
                    Log.e("FacilityError", "Network error: ${t.message}")
                }
            })
        }
    }
}
