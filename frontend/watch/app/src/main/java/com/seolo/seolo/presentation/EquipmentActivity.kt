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
import com.seolo.seolo.helper.SessionManager.selectedMachineName
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.MachineItem
import com.seolo.seolo.model.TaskItem
import com.seolo.seolo.model.TaskResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sh.tyy.wheelpicker.core.WheelPickerRecyclerView

// EquipmentActivity 클래스 정의
class EquipmentActivity : AppCompatActivity() {
    private lateinit var equipments: ArrayList<MachineItem>
    private var tasksTemplate: List<TaskItem> = emptyList()
    private var selectedMachineId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_wheel_picker_layout)
        supportActionBar?.hide()

        equipments = intent.getParcelableArrayListExtra("machines") ?: arrayListOf()
        val equipmentsName = listOf("　") + equipments.map { it.machineName } + listOf("　")
        val equipmentPicker = findViewById<WheelPickerRecyclerView>(R.id.basic_wheel_picker_view)
        val equipmentAdapter = WheelPickerAdapter(equipmentsName)
        equipmentPicker.adapter = equipmentAdapter

        equipmentPicker.post {
            val middlePosition = equipmentsName.size / 2
            equipmentPicker.layoutManager?.scrollToPosition(middlePosition)
        }

        equipmentPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                selectedMachineId = equipments.getOrNull(position)?.machineId.toString()
                selectedMachineName = equipments.getOrNull(position)?.machineName.toString()
            }
        })

        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            selectedMachineId?.let {
                SessionManager.selectedMachineId = it
                getTasks {
                    val intent = Intent(this@EquipmentActivity, WorkActivity::class.java)
                    intent.putExtra("tasks", ArrayList(it))
                    startActivity(intent)
                }
            }
        }
    }

    private fun getTasks(callback: (List<TaskItem>) -> Unit) {
        val token = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val service = RetrofitClient.taskService

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
