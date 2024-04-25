package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seolo.seolo.R

class CardFragment : Fragment() {
    // 다른 데이터나 로직이 필요하지 않다면 newInstance 메서드는 필요 없습니다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이트
        return inflater.inflate(R.layout.carousel_card, container, false)
    }

    // 필요한 경우 newInstance 메서드를 추가
    companion object {
        fun newInstance(): CardFragment {
            return CardFragment()
            // 필요한 경우 여기에 데이터를 추가
        }
    }
}

