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
import com.seolo.seolo.R
import com.seolo.seolo.helper.SessionManager
import com.seolo.seolo.presentation.DatePickerActivity

class TasksFragment : Fragment() {
    private var imageResourceId: Int = 0
    private var taskTemplateId: Int = 0
    private var taskTemplateName: String? = null
    private var taskPrecaution: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 전달된 인수를 사용하여 프래그먼트 초기화
            imageResourceId = it.getInt(ARG_IMAGE_RESOURCE_ID)
            taskTemplateId = it.getInt(ARG_TASK_TEMPLATE_ID)
            taskTemplateName = it.getString(ARG_TASK_TEMPLATE_NAME)
            taskPrecaution = it.getString(ARG_TASK_PRECAUTION)
        }
    }

    // Fragment의 UI를 생성하는 함수
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 레이아웃을 인플레이트하여 View 객체 생성
        val view = inflater.inflate(R.layout.work_layout, container, false)
        view.findViewById<ImageView>(R.id.workImageView).setImageResource(imageResourceId)
        view.findViewById<TextView>(R.id.workTextView).text = taskTemplateName

        // 클릭 이벤트 처리
        view.setOnClickListener {
            val context = view.context
            SessionManager.selectedTaskTemplateId = taskTemplateId.toString()
            SessionManager.selectedTaskPrecaution = taskPrecaution

            Log.d(
                "WorksFragment",
                "Task ID: $taskTemplateId, Task Name: $taskTemplateName, Precaution: $taskPrecaution"
            )
            // DatePickerActivity로 이동하는 Intent 생성 및 실행
            val intent = Intent(context, DatePickerActivity::class.java)
            context.startActivity(intent)
        }
        return view
    }

    companion object {
        // 전달할 인수의 키 값 정의
        private const val ARG_IMAGE_RESOURCE_ID = "imageResourceId"
        private const val ARG_TASK_TEMPLATE_ID = "taskTemplateId"
        private const val ARG_TASK_TEMPLATE_NAME = "taskTemplateName"
        private const val ARG_TASK_PRECAUTION = "taskPrecaution"

        // Fragment 인스턴스 생성을 위한 정적 메서드
        fun newInstance(
            imageResourceId: Int,
            taskTemplateId: Int,
            taskTemplateName: String,
            taskPrecaution: String
        ): TasksFragment {
            val fragment = TasksFragment()
            val args = Bundle().apply {
                putInt(ARG_IMAGE_RESOURCE_ID, imageResourceId)
                putInt(ARG_TASK_TEMPLATE_ID, taskTemplateId)
                putString(ARG_TASK_TEMPLATE_NAME, taskTemplateName)
                putString(ARG_TASK_PRECAUTION, taskPrecaution)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
