package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seolo.seolo.R

// CardFragment 클래스 정의
class CardFragment : Fragment() {
    // Fragment의 View를 생성하고 반환하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // carousel_layout 레이아웃을 inflate하여 반환
        return inflater.inflate(R.layout.carousel_layout, container, false)
    }

    // 정적 메서드를 사용하여 CardFragment 인스턴스를 생성하는 companion object
    companion object {
        fun newInstance(): CardFragment {
            return CardFragment()
        }
    }
}
