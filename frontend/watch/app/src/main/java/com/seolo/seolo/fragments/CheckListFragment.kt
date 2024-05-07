package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.presentation.CheckListActivity

class CheckListFragment : Fragment() {
    private var checklistText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            checklistText = it.getString(ARG_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.checklist_item_layout, container, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.textView)
        val textBox = view.findViewById<LinearLayout>(R.id.textbox)

        textView.text = checklistText

        textBox.setOnClickListener {
            val wasChecked = checkBox.isChecked
            checkBox.isChecked = !checkBox.isChecked
            if (!wasChecked) {
                view.postDelayed({
                    (activity as? CheckListActivity)?.moveToNextPage()
                }, 800)
            }
        }

        view.setOnClickListener {
            val wasChecked = checkBox.isChecked
            checkBox.isChecked = !checkBox.isChecked
            if (!wasChecked) {
                view.postDelayed({
                    (activity as? CheckListActivity)?.moveToNextPage()
                }, 800)
            }
        }

        return view
    }

    companion object {
        private const val ARG_TEXT = "arg_text"

        fun newInstance(text: String): CheckListFragment {
            return CheckListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
        }
    }
}
