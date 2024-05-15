package com.seolo.seolo.adapters

import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R

// WheelPickerAdapter 클래스 정의
class WheelPickerAdapter(private val selects: List<String>) :
    RecyclerView.Adapter<WheelPickerAdapter.ViewHolder>() {

    // ViewHolder 클래스 정의
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // TextView 선언 및 초기화
        val textView: TextView = view.findViewById(android.R.id.text1) as TextView

        // TextView의 텍스트 중앙 정렬 설정
        init {
            textView.gravity = Gravity.CENTER
        }
    }

    // ViewHolder 생성 및 반환하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 레이아웃 인플레이션
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_activated_1, parent, false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        // TextView의 너비와 높이 설정
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // 자동 줄바꿈 및 최대 줄 수 설정
        textView.setSingleLine(false)
        textView.maxLines = 4
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
        textView.textSize = 14f
        textView.setPadding(130, 0, 130, 0)

        return ViewHolder(view)
    }

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TextView에 선택 항목 텍스트 설정
        holder.textView.text = selects[position]
        // TextView 텍스트 색상 설정
        holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.SNOW))
    }

    // RecyclerView에 표시할 항목 개수 반환
    override fun getItemCount() = selects.size
}
