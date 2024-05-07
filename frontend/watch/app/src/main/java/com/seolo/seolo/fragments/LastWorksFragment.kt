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
import com.seolo.seolo.presentation.DatePickerActivity
import com.seolo.seolo.presentation.LOTOInfoActivity
import com.seolo.seolo.presentation.NFCActivity

// WorksFragment 클래스 정의
class LastWorksFragment : Fragment() {
    // 이미지 리소스 ID와 작업 정보를 저장하는 변수 선언
    private var imageResourceId: Int? = null
    private var work: String? = null

    // Fragment가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 전달된 인자에서 이미지 리소스 ID와 작업 정보를 가져와 변수에 저장
            imageResourceId = it.getInt(ARG_IMAGE_RESOURCE_ID)
            work = it.getString(ARG_WORK)
        }
    }

    // Fragment의 View를 생성하고 반환하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // work_layout 레이아웃을 inflate하여 View 객체 생성
        val view = inflater.inflate(R.layout.last_work_layout, container, false)

        // ImageView와 TextView를 찾아서 값을 설정
        val imageView: ImageView = view.findViewById(R.id.workImageView)
        imageResourceId?.let { imageView.setImageResource(it) }
        val textView: TextView = view.findViewById(R.id.workTextView)
        textView.text = work

        // View를 클릭하면 LOTOInfoActivity로 이동하는 이벤트 처리
        view.setOnClickListener {
            val context = view.context
            val intent = Intent(context, DatePickerActivity::class.java)
            context.startActivity(intent)
        }
        return view
    }

    // 이미지 리소스 ID와 작업 정보를 인자로 받아 WorksFragment 인스턴스를 생성하는 메서드
    companion object {
        private const val ARG_IMAGE_RESOURCE_ID = "imageResourceId"
        private const val ARG_WORK = "work"

        fun newInstance(imageResourceId: Int, work: String): LastWorksFragment {
            val fragment = LastWorksFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RESOURCE_ID, imageResourceId)
            args.putString(ARG_WORK, work)
            fragment.arguments = args
            return fragment
        }
    }
}
