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
import com.seolo.seolo.model.MachineItem
import com.seolo.seolo.model.TaskItem
import com.seolo.seolo.model.TaskResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

class EquipmentActivity : AppCompatActivity() {
    // 필드 초기화
    private lateinit var equipments: ArrayList<MachineItem>
    private var tasksTemplate: List<TaskItem> = emptyList()
    private var selectedMachineId: String? = null

    // 액티비티 생성 시 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.basic_wheel_picker_layout)

        // 인텐트로부터 기계 목록 가져오기
        equipments = intent.getParcelableArrayListExtra("machines") ?: arrayListOf()
        // 기계 이름 리스트 생성
        val equipmentsName = listOf("　") + equipments.map { it.machineName } + listOf("　")
        val equipmentPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val equipmentAdapter = WheelPickerAdapter(equipmentsName)
        equipmentPicker.adapter = equipmentAdapter

        equipmentPicker.post {
            val middlePosition = equipmentsName.size / 2
            equipmentPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        // 휠 피커에서 선택된 기계의 ID 및 이름 설정
        equipmentPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                selectedMachineId = equipments.getOrNull(position)?.machineId.toString()
                SessionManager.selectedMachineName = equipments.getOrNull(position)?.machineName.toString()
            }
        })

        // 확인 버튼 클릭 시 실행되는 리스너 설정
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            selectedMachineId?.let {
                SessionManager.selectedMachineId = it
                // 작업 목록 가져오기
                getTasks {
                    // WorkActivity로 이동하고 작업 목록 전달
                    val intent = Intent(this@EquipmentActivity, WorkActivity::class.java)
                    intent.putExtra("tasks", ArrayList(it))
                    startActivity(intent)
                }
            }
        }
    }

    // 작업 목록을 가져오는 메서드
    private fun getTasks(callback: (List<TaskItem>) -> Unit) {
        val token = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val service = RetrofitClient.taskService

        // 토큰 및 회사 코드가 유효한 경우 작업 목록 요청
        if (token != null && companyCode != null) {
            service.getTasks("Bearer $token", companyCode).enqueue(object : Callback<TaskResponse> {
                override fun onResponse(
                    call: Call<TaskResponse>, response: Response<TaskResponse>
                ) {
                    if (response.isSuccessful) {
                        tasksTemplate = response.body()?.tasks ?: listOf()
                        Log.d("TaskResponse", "Tasks: $tasksTemplate")
                        callback(tasksTemplate)
                    } else {
                        // 응답 실패 시 에러 로그 출력
                        Log.e("TaskError", "Failed to get tasks: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                    // 네트워크 오류 시 에러 로그 출력
                    Log.e("MachineError", "Network error: ${t.message}")
                }
            })
        }
    }
}
