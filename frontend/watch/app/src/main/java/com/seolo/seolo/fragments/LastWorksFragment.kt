// WorksFragment.kt
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
import com.seolo.seolo.presentation.DatePickerActivity

class LastWorksFragment : Fragment() {
    private var imageResourceId: Int = 0
    private var taskTemplateId: Int = 0
    private var taskTemplateName: String? = null
    private var taskPrecaution: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResourceId = it.getInt(ARG_IMAGE_RESOURCE_ID)
            taskTemplateId = it.getInt(ARG_TASK_TEMPLATE_ID)
            taskTemplateName = it.getString(ARG_TASK_TEMPLATE_NAME)
            taskPrecaution = it.getString(ARG_TASK_PRECAUTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.work_layout, container, false)
        view.findViewById<ImageView>(R.id.workImageView).setImageResource(imageResourceId)
        view.findViewById<TextView>(R.id.workTextView).text = taskTemplateName


        // View를 클릭하면 LOTOInfoActivity로 이동하는 이벤트 처리
        view.setOnClickListener {
            val context = view.context
            Log.d("WorksFragment", "Task ID: $taskTemplateId, Task Name: $taskTemplateName, Precaution: $taskPrecaution")
            val intent = Intent(context, DatePickerActivity::class.java)
            context.startActivity(intent)
        }

        return view
    }

    companion object {
        private const val ARG_IMAGE_RESOURCE_ID = "imageResourceId"
        private const val ARG_TASK_TEMPLATE_ID = "taskTemplateId"
        private const val ARG_TASK_TEMPLATE_NAME = "taskTemplateName"
        private const val ARG_TASK_PRECAUTION = "taskPrecaution"

        fun newInstance(
            imageResourceId: Int,
            taskTemplateId: Int,
            taskTemplateName: String,
            taskPrecaution: String
        ): WorksFragment {
            val fragment = WorksFragment()
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
