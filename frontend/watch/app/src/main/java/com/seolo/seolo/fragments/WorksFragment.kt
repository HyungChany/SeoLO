package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.presentation.LOTOInfoActivity
import com.seolo.seolo.presentation.NFCActivity

class WorksFragment : Fragment() {

    private var imageResourceId: Int? = null
    private var work: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResourceId = it.getInt(ARG_IMAGE_RESOURCE_ID)
            work = it.getString(ARG_WORK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이트
        val view = inflater.inflate(R.layout.work_layout, container, false)

        // 이미지 리소스 설정
        val imageView: ImageView = view.findViewById(R.id.workImageView)
        imageResourceId?.let { imageView.setImageResource(it) }

        val textView: TextView = view.findViewById(R.id.workTextView)
        textView.text = work

        view.setOnClickListener {
            val context = view.context
            val intent = Intent(context, NFCActivity::class.java)
            context.startActivity(intent)
        }
        return view
    }

    companion object {
        private const val ARG_IMAGE_RESOURCE_ID = "imageResourceId"
        private const val ARG_WORK = "work"

        fun newInstance(imageResourceId: Int, work: String): WorksFragment {
            val fragment = WorksFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RESOURCE_ID, imageResourceId)
            args.putString(ARG_WORK, work)
            fragment.arguments = args
            return fragment
        }
    }
}
