package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seolo.seolo.R

class LOTOInfoFragment : Fragment() {
    // 제목과 내용을 저장하는 변수 선언
    private var titleText: String? = null
    private var contentText: String? = null

    // Fragment가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 전달된 인자에서 제목과 내용을 가져와 변수에 저장
            titleText = it.getString(ARG_TITLE)
            contentText = it.getString(ARG_CONTENT)
        }
    }

    // Fragment의 View를 생성하고 반환하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.work_list_card_layout, container, false)
        // View 내부의 제목과 내용 TextView에 값 설정
        view.findViewById<TextView>(R.id.TitleView).text = titleText
        view.findViewById<TextView>(R.id.ContentView).text = contentText
        return view
    }

    // 제목과 내용을 인자로 받아 LOTOInfoFragment 인스턴스를 생성하는 메서드
    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"

        fun newInstance(title: String, content: String): LOTOInfoFragment {
            return LOTOInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }
}
