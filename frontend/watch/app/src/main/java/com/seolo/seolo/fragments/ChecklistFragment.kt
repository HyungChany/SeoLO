package com.seolo.seolo.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.seolo.seolo.R
import com.seolo.seolo.presentation.ChecklistActivity

class ChecklistFragment : Fragment() {
    private var checklistText: String? = null
    private var isChecked = false

    // Fragment가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 전달된 인자에서 체크리스트 텍스트를 가져와 변수에 저장
            checklistText = it.getString(ARG_TEXT)
        }
    }

    // Fragment의 View를 생성하고 반환하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // checklist_item_layout 레이아웃을 inflate하여 View 객체 생성
        val view = inflater.inflate(R.layout.checklist_item_layout, container, false)
        // View 내부의 요소들을 가져옴
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.textView)

        // TextView에 체크리스트 텍스트 설정
        textView.text = checklistText
        textView.movementMethod = ScrollingMovementMethod()

        // 체크박스를 클릭하면 상태 변경 및 액티비티에 알림
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
            (activity as? ChecklistActivity)?.onCheckboxCheckedChange(isChecked)
            if (isChecked) {
                Handler(Looper.getMainLooper()).postDelayed({
                    (activity as? ChecklistActivity)?.let { activity ->
                        val viewPager = activity.findViewById<ViewPager2>(R.id.viewPagerChecklist)
                        val currentItem = viewPager.currentItem
                        val totalItems = viewPager.adapter?.itemCount ?: 0
                        if (currentItem < totalItems - 1) {
                            viewPager.setCurrentItem(currentItem + 1, false)
                        }
                    }
                }, 800)
            }
        }

        // 뷰를 클릭하면 체크박스 상태 변경
        view.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }

        return view
    }

    // 체크리스트 텍스트를 인자로 받아 CheckListFragment 인스턴스를 생성하는 메서드
    companion object {
        private const val ARG_TEXT = "arg_text"

        fun newInstance(text: String): ChecklistFragment {
            return ChecklistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
        }
    }

    // 체크박스 상태 반환 메서드
    fun isChecked(): Boolean {
        return isChecked
    }
}
