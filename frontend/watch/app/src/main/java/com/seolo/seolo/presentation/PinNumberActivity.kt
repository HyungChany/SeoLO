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
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.authenticated) {
                        Toast.makeText(this@PinNumberActivity, "인증 성공!", Toast.LENGTH_SHORT).show()
                        navigateToMainActivity()
                    } else {
                        val failMessage =
                            "인증 실패: 실패 횟수 ${responseBody.fail_count}, 오류 코드: ${responseBody.auth_error_code}"
                        Toast.makeText(this@PinNumberActivity, failMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this@PinNumberActivity, "서버 에러: ${response.code()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PINResponse>, t: Throwable) {
                Toast.makeText(
                    this@PinNumberActivity, "통신 실패: ${t.message}", Toast.LENGTH_SHORT
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
