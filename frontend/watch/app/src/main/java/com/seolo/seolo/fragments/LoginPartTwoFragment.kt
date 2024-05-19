package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.helper.LotoManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.TokenResponse
import com.seolo.seolo.presentation.LoginActivity
import com.seolo.seolo.presentation.MainActivity
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPartTwoFragment : Fragment() {
    // 비밀번호 입력란과 로그인 버튼을 참조할 변수 선언
    private lateinit var inputPwText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 인플레이트하여 뷰 생성
        val view = inflater.inflate(R.layout.last_login_layout, container, false)

        // 뷰에서 EditText와 Button 참조
        inputPwText = view.findViewById(R.id.inputPwText)
        loginButton = view.findViewById(R.id.confirm_button)

        // 로그인 버튼 클릭 리스너 설정
        loginButton.setOnClickListener {
            login()
        }
        return view
    }

    private fun login() {
        // 부모 액티비티인 LoginActivity 인스턴스 가져오기
        val activity = activity as LoginActivity
        val username = activity.username
        val password = inputPwText.text.toString()
        val companyCode = activity.companyCode
        val deviceType = "watch"
        // 로그인 데이터 맵 구성
        val loginData = mapOf(
            "username" to username, "password" to password, "company_code" to companyCode
        )

        // 로그인 요청 데이터 로깅
        Log.d("LoginData", "Login request data: $loginData")

        // Retrofit을 사용하여 서버에 로그인 요청 보내기
        RetrofitClient.loginService.login(deviceType, loginData).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>, response: Response<TokenResponse>
            ) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    if (tokenResponse != null && tokenResponse.issuedToken.accessToken.isNotEmpty()) {
                        TokenManager.setAccessToken(
                            requireContext(), tokenResponse.issuedToken.accessToken
                        )
                        TokenManager.setCompanyCode(requireContext(), companyCode)
                        TokenManager.setUserName(requireContext(), username)
                        TokenManager.setUserId(requireContext(), tokenResponse.userId)
                        LotoManager.setLotoStatusCode(requireContext(), tokenResponse.codeStatus)
                        if (tokenResponse.workingLockerUid == null) {
                            LotoManager.setLotoUid(
                                requireContext(), ""
                            )
                        } else {
                            LotoManager.setLotoUid(requireContext(), tokenResponse.workingLockerUid)
                        }

                        if (tokenResponse.workingMachineId == null) {
                            LotoManager.setLotoMachineId(requireContext(), "")
                        } else {
                            LotoManager.setLotoMachineId(
                                requireContext(), tokenResponse.workingMachineId
                            )
                        }
                        tokenResponse.issuedCoreToken?.let {
                            LotoManager.setTokenValue(
                                requireContext(), it
                            )
                        }



                        // 로그인 성공 시 MainActivity로 이동 및 현재 액티비티 종료
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity.finish()
                    }
                } else {
                    // 로그인 실패 시 토스트 메시지 출력
                    Toast.makeText(
                        requireContext(), "사번, 비밀번호 또는 회사 코드가 틀렸습니다.", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // 통신 실패 시 토스트 메시지 출력
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(), "네트워크 연결을 다시 한 번 확인 해주세요.", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}