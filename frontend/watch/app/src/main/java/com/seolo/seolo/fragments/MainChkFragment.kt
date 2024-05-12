package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.helper.ChecklistManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.ChecklistResponse
import com.seolo.seolo.presentation.BluetoothActivity
import com.seolo.seolo.presentation.ChecklistActivity
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainChkFragment : Fragment() {
    private var content: String? = null

    companion object {
        // Fragment 인스턴스를 생성하는 메서드
        fun newInstance(content: String): MainChkFragment {
            val fragment = MainChkFragment()
            val args = Bundle()
            args.putString("content_key", content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 전달받은 content 데이터를 저장
        content = arguments?.getString("content_key")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 인플레이트하여 뷰 생성
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // TextView와 ImageView 참조
        val textView = view.findViewById<TextView>(R.id.textViewOverlay)
        textView.text = content
        val imageView = view.findViewById<ImageView>(R.id.gifImageView)

        // Glide를 사용하여 GIF 이미지 로드
        Glide.with(this).load(R.drawable.main_sample6).into(imageView)

        // View 클릭 리스너 설정
        view.setOnClickListener {
            // 체크리스트 데이터 가져오는 메서드 호출
            fetchChecklistData()
        }

        return view
    }

    private fun fetchChecklistData() {
        // 액세스 토큰과 회사 코드 가져오기
        val accessToken = TokenManager.getAccessToken(requireContext())
        val companyCode = TokenManager.getCompanyCode(requireContext())

        if (accessToken != null && companyCode != null) {
            // Retrofit을 사용하여 서버에서 체크리스트 데이터 가져오기
            RetrofitClient.checklistService.getChecklists("Bearer $accessToken", companyCode)
                .enqueue(object : Callback<ChecklistResponse> {
                    override fun onResponse(
                        call: Call<ChecklistResponse>, response: Response<ChecklistResponse>
                    ) {
                        if (response.isSuccessful) {
                            // 성공적으로 응답 받았을 때 처리
                            response.body()?.let { checklistResponse ->
                                // 가져온 체크리스트 데이터를 저장
                                ChecklistManager.setChecklist(
                                    requireContext(), checklistResponse.basicChecklists
                                )
                            }
                            // 체크리스트 액티비티로 이동
                            navigateToChecklistActivity()
                        } else {
                            // 서버 에러 처리
                            Log.d(
                                "MainChkFragment",
                                "Server returned an error: ${response.errorBody()?.string()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<ChecklistResponse>, t: Throwable) {
                        // 네트워크 에러 등의 처리
                        Log.d("MainChkFragment", "Failed to fetch data: ${t.message}")
                    }
                })
        }
    }

    private fun navigateToChecklistActivity() {
        // 체크리스트 액티비티로 이동하는 메서드
//        val intent = Intent(activity, ChecklistActivity::class.java)
        val intent = Intent(activity, BluetoothActivity::class.java)
        startActivity(intent)
    }

}
