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
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.FacilityItem
import com.seolo.seolo.model.MachineResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

// LocationActivity 클래스 정의
class LocationActivity : AppCompatActivity() {
    private lateinit var facilities: ArrayList<FacilityItem>
    private var selectedFacilityId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.basic_wheel_picker_layout)

        facilities = intent.getParcelableArrayListExtra("facilities") ?: arrayListOf()
        val facilityNames = listOf("　") + facilities.map { it.name } + listOf("　")

        val locationPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val locationAdapter = WheelPickerAdapter(facilityNames)
        locationPicker.adapter = locationAdapter

        locationPicker.post {
            val middlePosition = facilityNames.size / 2
            locationPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        // 선택된 위치의 ID를 저장
        locationPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                selectedFacilityId = facilities.getOrNull(position)?.id.toString()
            }
        })

        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            selectedFacilityId?.let {
                getMachines(it)
            }
        }
    }

    // 기계 목록 요청 메서드
    private fun getMachines(facilityId: String) {
        val token = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val service = RetrofitClient.machineService

        if (token != null && companyCode != null) {
            service.getMachines( "Bearer $token", companyCode, facilityId).enqueue(object :
                Callback<MachineResponse> {
                override fun onResponse(call: Call<MachineResponse>, response: Response<MachineResponse>) {
                    if (response.isSuccessful) {
                        val machines = response.body()?.machines ?: listOf()
                        Log.d("MachineResponse", "Machines: $machines")
                        val intent = Intent(this@LocationActivity, EquipmentActivity::class.java)
                        intent.putExtra("machines", ArrayList(machines))
                        startActivity(intent)
                    } else {
                        Log.e("MachineError", "Error fetching machines: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<MachineResponse>, t: Throwable) {
                    Log.e("MachineError", "Network error: ${t.message}")
                }
            })
        }
    }
}
