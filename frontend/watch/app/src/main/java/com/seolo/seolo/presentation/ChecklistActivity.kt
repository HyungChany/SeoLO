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
import com.seolo.seolo.model.ChecklistItem
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
    private var checklistItemCount: Int = 0
    private var checkedItemCount: Int = 0

    // 액티비티 생성 시 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        supportActionBar?.hide()
        setContentView(R.layout.checklist_layout)

        viewPager = findViewById(R.id.viewPagerChecklist)
        val adapter = CarouselStateAdapter(this)

        val basicChecklistItems = ChecklistManager.getBasicChecklist(this) ?: emptyList()
        val checklistTextItems = ChecklistManager.getChecklistText(this) ?: emptyList()
        val checklistItems: List<ChecklistItem> = basicChecklistItems + checklistTextItems

        // 체크리스트가 비어있지 않으면 각 아이템을 프래그먼트로 추가
        if (checklistItems.isNotEmpty()) {
            checklistItemCount = checklistItems.size
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

    // 체크박스 상태가 변경될 때 호출
    fun onCheckboxCheckedChange(isChecked: Boolean) {
        if (isChecked) {
            checkedItemCount++
        } else {
            checkedItemCount--
        }

        // 모든 체크박스가 체크되었을 경우 다음 액티비티로 전환
        if (checkedItemCount == checklistItemCount) {
            moveToNextPage()
        }
    }

    // 다음 페이지로 이동
    private fun moveToNextPage() {
        // 시설물 정보를 담아 FacilityActivity로 이동
        val intent = Intent(this, FacilityActivity::class.java)
        intent.putExtra("facilities", ArrayList(facilities))
        startActivity(intent)
    }

    // 시설물 정보를 가져오기
    private fun getFacilities() {
        val accessToken = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val username = TokenManager.getUserName(this)
        val deviceType = "watch"
        if (accessToken != null && companyCode != null && username != null) {
            // Retrofit을 사용하여 서버로부터 시설물 정보 요청
            RetrofitClient.facilityService.getFacilities(
                "Bearer $accessToken", companyCode, deviceType, username
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
