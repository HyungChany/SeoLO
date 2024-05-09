package com.seolo.seolo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.databinding.FragmentMainBinding
import com.seolo.seolo.presentation.ChecklistActivity
import com.seolo.seolo.presentation.NFCActivity

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var overlayText: String? = null
    private var pagePosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 여기에서 arguments를 통해 값을 받아와 초기화합니다.
        overlayText = arguments?.getString("overlayText")
        pagePosition = arguments?.getInt("pagePosition") ?: 0

        overlayText?.let {
            binding.textViewOverlay.text = it
        }
        Glide.with(this).asGif().load(R.drawable.main_sample6).into(binding.gifImageView)

        binding.root.setOnClickListener {
            val targetActivity = if (pagePosition == 0) ChecklistActivity::class.java else NFCActivity::class.java
            startActivity(Intent(requireContext(), targetActivity))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(text: String, position: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString("overlayText", text)
                    putInt("pagePosition", position)
                }
            }
        }
    }
}
