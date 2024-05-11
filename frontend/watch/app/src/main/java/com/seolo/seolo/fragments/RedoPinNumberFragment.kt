package com.seolo.seolo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.PINRequest
import com.seolo.seolo.model.PINResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RedoPinNumberFragment : Fragment() {

    companion object {
        fun newInstance(): RedoPinNumberFragment {
            return RedoPinNumberFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.redo_pin_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.confirm_button).setOnClickListener {
            onConfirmButtonClick(view)
        }
    }

    private fun onConfirmButtonClick(view: View) {
        val pinNumberEditText = view.findViewById<EditText>(R.id.pinNumberEditText)
        val pinNumber = pinNumberEditText.text.toString()
        sendPinToServer(pinNumber)
    }

    private fun sendPinToServer(pinNumber: String) {
        val accessToken = TokenManager.getAccessToken(requireContext())
        val companyCode = TokenManager.getCompanyCode(requireContext())

        val pinRequest = PINRequest(pinNumber)

        companyCode?.let {
            RetrofitClient.pinService.sendPinNumber(
                "Bearer $accessToken", it, pinRequest
            )
        }?.enqueue(object : Callback<PINResponse> {
            override fun onResponse(call: Call<PINResponse>, response: Response<PINResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.authenticated) {
                        Toast.makeText(requireContext(), "인증 성공!", Toast.LENGTH_SHORT).show()
                    } else {
                        val failMessage =
                            "인증 실패: 실패 횟수 ${responseBody.fail_count}, 오류 코드: ${responseBody.auth_error_code}"
                        Toast.makeText(requireContext(), failMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(), "서버 에러: ${response.code()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PINResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(), "통신 실패: ${t.message}", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}