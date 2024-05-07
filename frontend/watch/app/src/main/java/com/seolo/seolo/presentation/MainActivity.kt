package com.seolo.seolo.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pl.droidsonroids.gif.GifDrawable
import com.seolo.seolo.R
import com.seolo.seolo.databinding.MainLayoutBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gifFromResource = GifDrawable(resources, R.drawable.main_sample3)
        gifFromResource.setSpeed(2.0f)

        binding.gifImageView.setImageDrawable(gifFromResource)

        binding.textViewOverlay.text = "LOTO\n잠금"
        binding.root.setOnClickListener {
            val intent = Intent(this, CheckListActivity::class.java)
            startActivity(intent)
        }
    }
}
