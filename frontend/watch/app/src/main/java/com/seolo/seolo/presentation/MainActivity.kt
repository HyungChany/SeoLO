/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.seolo.seolo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seolo.seolo.databinding.MainLayoutBinding
import com.bumptech.glide.Glide
import com.seolo.seolo.R

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.main_sample3).into(binding.mainView)

        // 오버레이 텍스트 설정
        binding.textViewOverlay.text = "LOTO\n잠금"
    }
}
