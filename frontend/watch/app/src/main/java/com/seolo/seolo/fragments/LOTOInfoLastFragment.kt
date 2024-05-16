package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.helper.SessionManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.IssueResponse
import com.seolo.seolo.model.LotoInfo
import com.seolo.seolo.presentation.BluetoothLOTOActivity
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LOTOInfoLastFragment : Fragment() {
    private var titleText: String? = null
    private var contentText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleText = it.getString(ARG_TITLE)
            contentText = it.getString(ARG_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.last_work_list_card_layout, container, false)
        view.findViewById<TextView>(R.id.TitleView).text = titleText
        view.findViewById<TextView>(R.id.ContentView).text = contentText

        view.setOnClickListener {
            showConfirmationDialog()
        }

        return view
    }

    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(
            requireActivity(), com.google.android.material.R.style.AlertDialog_AppCompat_Light
        ).setTitle(" ").setMessage("Bluetooth 연결을 시작 하시겠습니까?").setPositiveButton("확인", null)
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }.create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            issueCoreLogic()
            val intent = Intent(activity, BluetoothLOTOActivity::class.java)
            startActivity(intent)
            Handler(requireActivity().mainLooper).postDelayed({
                dialog.dismiss()
            }, 200)
        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"

        fun newInstance(title: String, content: String): LOTOInfoLastFragment {
            return LOTOInfoLastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }

    private fun issueCoreLogic() {
        val authorization = "Bearer " + TokenManager.getAccessToken(requireContext())
        val companyCode = TokenManager.getCompanyCode(requireContext())
        val deviceType = "watch"

        val lotoInfo = LotoInfo(
            locker_uid = "",
            battery_info = "",
            machine_id = SessionManager.selectedMachineId ?: "",
            task_template_id = SessionManager.selectedTaskTemplateId ?: "",
            task_precaution = SessionManager.selectedTaskPrecaution ?: "",
            end_time = SessionManager.selectedDate + "T" + SessionManager.selectedTime
        )

        val call = RetrofitClient.issueService.sendLotoInfo(
            authorization = authorization,
            companyCode = companyCode ?: "",
            deviceType = deviceType,
            lotoInfo = lotoInfo
        )

        call.enqueue(object : Callback<IssueResponse> {
            override fun onResponse(call: Call<IssueResponse>, response: Response<IssueResponse>) {
                if (response.isSuccessful) {
                    val issueResponse = response.body()
                    Log.d("token_value, ", "Response.next_code: ${issueResponse?.next_code}")
                    Log.d("token_value, ", "Response.token_value: ${issueResponse?.token_value}")
                } else {
                    Log.d(
                        "Failed, ", "Failed: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<IssueResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
