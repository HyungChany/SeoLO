package com.seolo.seolo.presentation

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R
import com.seolo.seolo.adapters.BluetoothAdapter
import com.seolo.seolo.adapters.BluetoothDeviceAdapter
import java.util.UUID

class BluetoothPickActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null
    private lateinit var connectingToast: Toast

    companion object {
        private const val REQUEST_BLUETOOTH_PERMISSION = 101

        // Bluetooth 서비스와 캐릭터리스틱의 UUID 정의
        private val SERVICE_UUID = UUID.fromString("20240520-C104-C104-C104-012345678910")
        private val CHAR_UUID = UUID.fromString("20240521-C104-C104-C104-012345678910")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_layout)
        supportActionBar?.hide()

        // RecyclerView 초기화 및 설정
        recyclerView = findViewById(R.id.bluetoothDeviceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerView 어댑터 설정
        deviceAdapter = BluetoothDeviceAdapter(this, devices) { device ->
            onDeviceSelected(device)
        }
        recyclerView.adapter = deviceAdapter

        // RecyclerView에 스냅 헬퍼 추가
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // BluetoothAdapter 초기화
        bluetoothAdapter = BluetoothAdapter(this)

        // Bluetooth 권한 확인 후 디바이스 검색 시작
        if (!bluetoothAdapter.checkBluetoothPermissions()) {
            bluetoothAdapter.requestBluetoothPermissions()
        } else {
            bluetoothAdapter.startDiscoveryForSpecificDevices("SEOLO LOCK") { newDevices ->
                deviceAdapter.updateDevices(newDevices)
            }
        }
    }

    // 기기 선택 시 호출되는 함수
    @RequiresApi(Build.VERSION_CODES.S)
    private fun onDeviceSelected(device: BluetoothDevice) {
        // 기기 선택 시 GATT 스캐닝 중지
        bluetoothAdapter.stopDiscovery()

        // "연결 중입니다." 토스트 메시지 표시
        connectingToast = Toast.makeText(this, "연결 중입니다.", Toast.LENGTH_SHORT)
        connectingToast.show()

        // 100ms 딜레이 후 기기 연결 시도
        Handler(Looper.getMainLooper()).postDelayed({
            connectToDevice(device)
        }, 100)
    }

    // 기기 연결 로직을 포함한 함수
    @RequiresApi(Build.VERSION_CODES.S)
    private fun connectToDevice(device: BluetoothDevice) {
        // Bluetooth 연결 권한 확인
        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // Bluetooth GATT로 기기 연결 시작 (BluetoothDevice.TRANSPORT_LE 사용)
            bluetoothGatt = device.connectGatt(this, false, gattClientCallback, BluetoothDevice.TRANSPORT_LE)
        } else {
            // 권한이 없으면 사용자에게 권한 요청
            requestPermissions(
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION
            )
        }
    }

    // GATT 콜백 정의
    private val gattClientCallback = object : BluetoothGattCallback() {

        // 연결 상태 변경 시 호출되는 콜백
        @RequiresApi(Build.VERSION_CODES.S)
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            Log.d("BluetoothGattCallback", "Status: $status, New State: $newState")

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 기기가 연결될 때
                try {
                    if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("BluetoothActivity", "Device connected: ${gatt?.device?.name}")
                    }
                } catch (e: SecurityException) {
                    Log.e("BluetoothActivity", "Permission error: ${e.message}")
                }

                // Toast 메시지와 화면 전환을 메인 스레드에서 실행
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@BluetoothPickActivity, "연결 성공", Toast.LENGTH_SHORT).show()

                    // ConnectActivity로 전환
                    val intent = Intent(this@BluetoothPickActivity, ChecklistActivity::class.java)
                    startActivity(intent)
                    finish() // 현재 액티비티 종료
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // 기기가 연결이 끊겼을 때
                Log.d("BluetoothActivity", "Device disconnected")
                if (status == BluetoothGatt.GATT_FAILURE || status == 133) {
                    // 연결 실패 또는 특정 오류 코드에서 재시도
                    Log.d("BluetoothActivity", "Attempting to reconnect...")
                    try {
                        gatt?.close()
                    } catch (e: SecurityException) {
                        Log.e("BluetoothActivity", "Permission error: ${e.message}")
                    }
                    bluetoothGatt = null
                    Handler(Looper.getMainLooper()).postDelayed({
                        gatt?.device?.let { connectToDevice(it) }  // 3초 후 재시도
                    }, 3000)
                }
            }
        }
    }

    // Bluetooth 권한 확인 및 Bluetooth 활성화 함수
    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkBluetoothPermissions() {
        if (bluetoothAdapter.isBluetoothEnabled()) {
            bluetoothAdapter.startDiscovery()
        } else {
            bluetoothAdapter.createEnableBluetoothIntent()?.let {
                startActivityForResult(it, bluetoothAdapter.REQUEST_ENABLE_BT)
            }
        }
    }

    // Bluetooth 활성화 결과 처리 함수
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == bluetoothAdapter.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            bluetoothAdapter.startDiscovery()
        }
    }

    // Bluetooth 권한 요청 결과 처리 함수
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == bluetoothAdapter.REQUEST_BLUETOOTH_SCAN && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            bluetoothAdapter.startDiscoveryWithPermissions()
        } else {
            Log.e("BluetoothActivity", "Required permissions not granted.") // 권한 요청 거부 처리
        }
    }

    // 액티비티 종료 시 BluetoothAdapter 정리
    override fun onDestroy() {
        try {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothGatt?.close() // 권한이 있을 때 GATT 연결 해제
            }
        } catch (e: SecurityException) {
            Log.e("BluetoothActivity", "Permission error: ${e.message}")
        } finally {
            bluetoothAdapter.cleanup()
            super.onDestroy()
        }
    }
}
