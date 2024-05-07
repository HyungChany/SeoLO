package com.seolo.seolo.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.seolo.seolo.presentation.NFCActivity

class LastWorkListFragment : Fragment() {
    private var titleText: String? = null
    private var contentText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleText = it.getString(ARG_TITLE)
            contentText = it.getString(ARG_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.last_work_list_card_layout, container, false)
        view.findViewById<TextView>(R.id.TitleView).text = titleText
        view.findViewById<TextView>(R.id.ContentView).text = contentText

        view.setOnClickListener {
            showConfirmationDialog()
        }

        return view
    }

    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(
            requireActivity(), com.google.android.material.R.style.AlertDialog_AppCompat_Light
        ).setTitle(" ").setMessage("NFC 기능을 시작하시겠습니까?").setPositiveButton("확인", null)
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.cancel()
            }).create()

        dialog.show()

        // 버튼 클릭 리스너를 다이얼로그가 표시된 후에 설정합니다.
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val intent = Intent(activity, NFCActivity::class.java)
            startActivity(intent)
            Handler(requireActivity().mainLooper).postDelayed({
                dialog.dismiss()
            }, 200)
        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"

        fun newInstance(title: String, content: String): LastWorkListFragment {
            return LastWorkListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }
}
