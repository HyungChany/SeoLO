package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.TokenResponse
import com.seolo.seolo.presentation.LoginActivity
import com.seolo.seolo.presentation.MainActivity
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPartTwoFragment : Fragment() {

    private lateinit var inputPwText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.last_login_layout, container, false)
        inputPwText = view.findViewById(R.id.inputPwText)
        loginButton = view.findViewById(R.id.confirm_button)

        loginButton.setOnClickListener {
            login()
        }
        return view
    }

    private fun login() {
        val activity = activity as LoginActivity
        val username = activity.username
        val password = inputPwText.text.toString()
        val companyCode = activity.companyCode

        val loginData = mapOf(
            "username" to username, "password" to password, "company_code" to companyCode
        )

        Log.d("LoginData", "Login request data: $loginData")

        RetrofitClient.loginService.login(loginData).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>, response: Response<TokenResponse>
            ) {
                response.body()?.let { tokenResponse ->
                    if (tokenResponse.issuedToken.accessToken.isNotEmpty()) {

                        // 토큰 저장
                        TokenManager.setAccessToken(
                            requireContext(), tokenResponse.issuedToken.accessToken
                        )
                        TokenManager.setCompanyCode(requireContext(), companyCode)
                        TokenManager.setUserName(requireContext(), username)

                        // 로그인 성공 시 처리 로직
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity.finish()
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                // 로그인 실패 시 처리 로직
            }
        })
    }
}
