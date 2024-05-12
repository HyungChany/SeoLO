package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.presentation.LoginActivity

// 로그인 화면의 프래그먼트 클래스
class LoginPartOneFragment : Fragment() {

    companion object {
        private const val ARG_HINT = "hint"

        // 프래그먼트 인스턴스 생성 메서드
        fun newInstance(hint: String): LoginPartOneFragment {
            val fragment = LoginPartOneFragment()
            val args = Bundle()
            args.putString(ARG_HINT, hint)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var editText: EditText

    // 프래그먼트의 UI를 생성하는 함수
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이트하여 View 객체 생성
        val view = inflater.inflate(R.layout.login_layout, container, false)
        editText = view.findViewById(R.id.EditText)
        val hint = arguments?.getString(ARG_HINT)
        editText.hint = hint

        // 다음 버튼 클릭 이벤트 처리
        val nextButton: Button = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            if (hint == "회사코드를 입력하세요.") {
                // LoginActivity의 companyCode 설정
                (activity as LoginActivity).companyCode = editText.text.toString()
            } else if (hint == "사번을 입력하세요.") {
                // LoginActivity의 username 설정
                (activity as LoginActivity).username = editText.text.toString()
            }

            // LoginActivity의 nextPage 호출
            (activity as LoginActivity).nextPage()
        }

        return view
    }
}
