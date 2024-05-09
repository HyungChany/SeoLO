package com.seolo.seolo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seolo.seolo.R
import com.bumptech.glide.Glide


class MainChkFragment : Fragment() {
    private var content: String? = null

    companion object {
        fun newInstance(content: String): MainChkFragment {
            val fragment = MainChkFragment()
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val textView = view.findViewById<TextView>(R.id.textViewOverlay)
        textView.text = content

        val imageView = view.findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).load(R.drawable.main_sample6).into(imageView)

        return view
    }
}