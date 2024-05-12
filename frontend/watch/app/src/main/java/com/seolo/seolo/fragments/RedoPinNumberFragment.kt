package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.NewPINRequest
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

        val newPinRequest = NewPINRequest(pinNumber)

        if (companyCode != null) {
            RetrofitClient.NewPinService.sendPinNumber(
                "Bearer $accessToken", companyCode, newPinRequest
            ).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>, response: Response<String>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        if (responseBody == "PIN 변경 성공 로그아웃 시켜주세요") {
                            Toast.makeText(
                                requireContext(), "PIN 변경 성공. 로그아웃합니다.", Toast.LENGTH_SHORT
                            ).show()
                            TokenManager.clearTokens(requireContext())
                            Intent(requireContext(), SplashActivity::class.java).apply {
                                startActivity(this)
                                requireActivity().finish()
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(), "서버 에러: ${response.code()}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("PIN Change Error", "통신 실패", t)
                    Toast.makeText(
                        requireContext(), "통신 실패: ${t.localizedMessage}", Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }
}
