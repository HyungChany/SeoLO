package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.model.TokenResponse
import com.seolo.seolo.services.RetrofitClient
import com.seolo.seolo.services.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// LoginActivity 클래스 정의
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        supportActionBar?.hide()

        RetrofitClient.apiService.getToken().enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                response.body()?.let { tokenResponse ->
                    if (tokenResponse.issuedToken.accessToken.isNotEmpty()) {
                        // 토큰 저장
                        TokenManager.setAccessToken(this@LoginActivity, tokenResponse.issuedToken.accessToken)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                // 오류 처리
            }
        })
    }
}
