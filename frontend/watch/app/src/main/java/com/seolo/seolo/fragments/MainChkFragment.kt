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
import com.seolo.seolo.presentation.LocationActivity
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainChkFragment : Fragment() {
    private var content: String? = null

    companion object {
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
        content = arguments?.getString("content_key")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val textView = view.findViewById<TextView>(R.id.textViewOverlay)
        textView.text = content

        val imageView = view.findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).load(R.drawable.main_sample6).into(imageView)

        view.setOnClickListener {
            fetchChecklistData()
        }

        return view
    }

    private fun fetchChecklistData() {
        val accessToken = TokenManager.getAccessToken(requireContext())
        val companyCode = TokenManager.getCompanyCode(requireContext())

        if (accessToken != null && companyCode != null) {
            RetrofitClient.checklistService.getChecklists("Bearer $accessToken", companyCode)
                .enqueue(object : Callback<ChecklistResponse> {
                    override fun onResponse(
                        call: Call<ChecklistResponse>, response: Response<ChecklistResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { checklistResponse ->
                                ChecklistManager.setChecklist(
                                    requireContext(), checklistResponse.basic_checklists
                                )
                            }
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
}
