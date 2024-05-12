package com.seolo.seolo.presentation

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.seolo.seolo.R
import com.seolo.seolo.adapters.BluetoothAdapter
import com.seolo.seolo.databinding.BluetoothLayoutBinding

class BluetoothActivity : AppCompatActivity() {
    private lateinit var binding: BluetoothLayoutBinding
    private lateinit var bluetoothAdapter: BluetoothAdapter

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()

        // View Binding 초기화
        binding = BluetoothLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Glide를 사용하여 GIF 이미지 로딩
        Glide.with(this).asGif().load(R.drawable.bluetooth).into(binding.bluetoothView)

        // ImageView에 일반 이미지 로딩
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.img_nfc)
        binding.bluetoothView.setImageDrawable(drawable)

        //BluetoothAdapter 초기화
        bluetoothAdapter = BluetoothAdapter(this)

        // Bluetooth 활성화 요청 및 검색 시작
        if (bluetoothAdapter.isBluetoothEnabled()) {
            // Bluetooth가 이미 활성화되어 있으면 바로 검색 시작
            bluetoothAdapter.startDiscovery()
        } else {
            // Bluetooth가 비활성화되어 있으면 활성화 요청
            bluetoothAdapter.createEnableBluetoothIntent()?.let {
                startActivityForResult(it, bluetoothAdapter.REQUEST_ENABLE_BT)
            }
        }
    }

    // Activity 결과 처리
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == bluetoothAdapter.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            // Bluetooth가 활성화되면 디바이스 검색을 시작
            bluetoothAdapter.startDiscovery()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothAdapter.cleanup() // 리시버 등록 해제
    }
}


//        // 화면 터치 리스너 설정
//        binding.root.setOnClickListener {
//            // CheckListActivity로 이동하는 Intent 생성
//            val intent = Intent(this, ChecklistActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}
