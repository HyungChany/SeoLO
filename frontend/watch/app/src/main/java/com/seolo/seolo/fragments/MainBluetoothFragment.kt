package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.presentation.BluetoothMainActivity

class MainBluetoothFragment : Fragment() {
    private var content: String? = null

    companion object {
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
        content = arguments?.getString("content_key")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 인플레이트하여 뷰 생성
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val textView = view.findViewById<TextView>(R.id.textViewOverlay)
        textView.text = content
        val imageView = view.findViewById<ImageView>(R.id.gifImageView)

        // Glide를 사용하여 GIF 이미지 로드
        Glide.with(this).load(R.drawable.main_sample6).into(imageView)

        // 터치 이벤트를 감지하여 BluetoothActivity로 이동
        view.setOnClickListener {
            val intent = Intent(activity, BluetoothMainActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
