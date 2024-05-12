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
import com.seolo.seolo.presentation.BluetoothActivity

// LastWorkListFragment 클래스 정의
class LOTOInfoLastFragment : Fragment() {
    // 제목과 내용을 저장하는 변수 선언
    private var titleText: String? = null
    private var contentText: String? = null

    // Fragment가 생성될 때 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 전달된 인자에서 제목과 내용을 가져와 변수에 저장
            titleText = it.getString(ARG_TITLE)
            contentText = it.getString(ARG_CONTENT)
        }
    }

    // Fragment의 View를 생성하고 반환하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // last_work_list_card_layout 레이아웃을 inflate하여 View 객체 생성
        val view = inflater.inflate(R.layout.last_work_list_card_layout, container, false)
        // View 내부의 제목과 내용 TextView에 값 설정
        view.findViewById<TextView>(R.id.TitleView).text = titleText
        view.findViewById<TextView>(R.id.ContentView).text = contentText

        // View를 클릭하면 확인 다이얼로그를 표시하는 메서드 호출
        view.setOnClickListener {
            showConfirmationDialog()
        }

        return view
    }

    // Bluetooth 기능을 시작할지 여부를 묻는 확인 다이얼로그를 표시하는 메서드
    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(
            requireActivity(), com.google.android.material.R.style.AlertDialog_AppCompat_Light
        ).setTitle(" ").setMessage("Bluetooth 연결을 시작 하시겠습니까?").setPositiveButton("확인", null)
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.cancel()
            }).create()

        dialog.show()

        // 확인 버튼 클릭 시 BluetoothActivity 이동하고 다이얼로그를 닫는 이벤트 처리
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val intent = Intent(activity, BluetoothActivity::class.java)
            startActivity(intent)
            Handler(requireActivity().mainLooper).postDelayed({
                dialog.dismiss()
            }, 200)
        }
    }

    // 제목과 내용을 인자로 받아 LastWorkListFragment 인스턴스를 생성하는 메서드
    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"

        fun newInstance(title: String, content: String): LOTOInfoLastFragment {
            return LOTOInfoLastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }
}
