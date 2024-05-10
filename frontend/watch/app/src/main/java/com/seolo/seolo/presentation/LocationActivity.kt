package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R
import com.seolo.seolo.adapters.WheelPickerAdapter
import com.seolo.seolo.helper.SessionManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.FacilityItem
import com.seolo.seolo.model.MachineResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

class LocationActivity : AppCompatActivity() {
    // 필드 초기화
    private lateinit var facilities: ArrayList<FacilityItem>
    private var selectedFacilityId: String? = null

    // 액티비티 생성 시 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.basic_wheel_picker_layout)

        // 인텐트로부터 시설물 목록 가져오기
        facilities = intent.getParcelableArrayListExtra("facilities") ?: arrayListOf()
        // 시설물 이름 리스트 생성
        val facilityNames = listOf("　") + facilities.map { it.name } + listOf("　")

        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val locationAdapter = WheelPickerAdapter(facilityNames)
        locationPicker.adapter = locationAdapter

        locationPicker.post {
            val middlePosition = facilityNames.size / 2
            locationPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        // 휠 피커에서 선택된 시설물의 ID 및 이름 설정
        locationPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                selectedFacilityId = facilities.getOrNull(position)?.id.toString()
                SessionManager.selectedFacilityName = facilities.getOrNull(position)?.name.toString()
            }
        })

        // 확인 버튼 클릭 시 실행되는 리스너 설정
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            selectedFacilityId?.let {
                // 해당 시설물의 기계 목록 가져오기
                getMachines(it)
            }
        }
    }

    // 기계 목록을 가져오는 메서드
    private fun getMachines(facilityId: String) {
        val token = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val service = RetrofitClient.machineService

        // 토큰 및 회사 코드가 유효한 경우 기계 목록 요청
        if (token != null && companyCode != null) {
            service.getMachines("Bearer $token", companyCode, facilityId).enqueue(object :
                Callback<MachineResponse> {
                override fun onResponse(call: Call<MachineResponse>, response: Response<MachineResponse>) {
                    if (response.isSuccessful) {
                        val machines = response.body()?.machines ?: listOf()
                        Log.d("MachineResponse", "Machines: $machines")
                        // EquipmentActivity로 이동하고 기계 목록 전달
                        val intent = Intent(this@LocationActivity, EquipmentActivity::class.java)
                        intent.putExtra("machines", ArrayList(machines))
                        startActivity(intent)
                    } else {
                        // 응답 실패 시 에러 로그 출력
                        Log.e("MachineError", "Error fetching machines: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<MachineResponse>, t: Throwable) {
                    // 네트워크 오류 시 에러 로그 출력
                    Log.e("MachineError", "Network error: ${t.message}")
                }
            })
        }
    }
}
