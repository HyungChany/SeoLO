package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.bumptech.glide.Glide

class MainBluetoothFragment : Fragment() {
    private var content: String? = null

    companion object {
        // Fragment 인스턴스를 생성하는 메서드
        fun newInstance(content: String): MainBluetoothFragment {
            val fragment = MainBluetoothFragment()
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

        return view
    }
}
