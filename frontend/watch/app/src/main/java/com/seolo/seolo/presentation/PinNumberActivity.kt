package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.PINRequest
import com.seolo.seolo.model.PINResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pin_layout)
        supportActionBar?.hide()
    }

    fun onConfirmButtonClick(view: View) {
        val pinNumberEditText = findViewById<EditText>(R.id.pinNumberEditText)
        val pinNumber = pinNumberEditText.text.toString()
        sendPinToServer(pinNumber)
    }

    private fun sendPinToServer(pinNumber: String) {
        val accessToken = TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)

        val pinRequest = PINRequest(pinNumber)

        companyCode?.let {
            RetrofitClient.pinService.sendPinNumber(
                "Bearer $accessToken", it, pinRequest
            )
        }?.enqueue(object : Callback<PINResponse> {
            override fun onResponse(call: Call<PINResponse>, response: Response<PINResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.authenticated) {
                            Toast.makeText(this@PinNumberActivity, "인증 성공!", Toast.LENGTH_SHORT)
                                .show()
                            navigateToMainActivity()
                        } else {
                            val failMessage = "실패 횟수 ${responseBody.fail_count}"
                            Toast.makeText(
                                this@PinNumberActivity,
                                "PIN 번호가 틀렸습니다. 실패횟수:${failMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // 서버 응답은 성공했지만 body가 null인 경우
                        Toast.makeText(
                            this@PinNumberActivity, "서버 응답이 올바르지 않습니다.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // 서버 응답 실패
                    Toast.makeText(
                        this@PinNumberActivity, "서버 응답이 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PINResponse>, t: Throwable) {
                // 네트워크 연결 실패
                Toast.makeText(
                    this@PinNumberActivity, "네트워크에 연결할 수 없습니다.", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
