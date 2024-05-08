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

class LoginFragment : Fragment() {
    companion object {
        private const val ARG_HINT = "hint"

        fun newInstance(hint: String): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putString(ARG_HINT, hint)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var numberEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_layout, container, false)
        numberEditText = view.findViewById(R.id.numberEditText)
        val hint = arguments?.getString(ARG_HINT)
        numberEditText.hint = hint

        val nextButton: Button = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            // LoginActivity의 변수에 값을 저장
            if (hint == "회사코드를 입력하세요.") {
                (activity as LoginActivity).companyCode = numberEditText.text.toString()
            } else if (hint == "사번을 입력하세요.") {
                (activity as LoginActivity).employeeNumber = numberEditText.text.toString()
            }

            (activity as LoginActivity).nextPage()
        }

        return view
    }
}
