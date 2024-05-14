package com.seolo.seolo.presentation

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
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
import com.seolo.seolo.helper.SessionManager
import com.seolo.seolo.helper.TokenManager
import java.nio.charset.StandardCharsets
import java.util.UUID

class BluetoothPickActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null

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
        recyclerView = findViewById(R.id.bluetoothListItem)
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

        // 100ms 딜레이 후 기기 연결 시도
        Handler(Looper.getMainLooper()).postDelayed({
            connectToDevice(device)
        }, 100)
    }

    // 기기 연결 및 데이터 전송 로직을 포함한 함수
    @RequiresApi(Build.VERSION_CODES.S)
    private fun connectToDevice(device: BluetoothDevice) {
        // Bluetooth 연결 권한 확인
        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            val deviceName = device.name ?: "Unknown Device"
            Toast.makeText(this@BluetoothPickActivity, "$deviceName 와 연결중입니다.", Toast.LENGTH_SHORT)
                .show()

            // Bluetooth GATT로 기기 연결 시작 (BluetoothDevice.TRANSPORT_LE 사용)
            bluetoothGatt =
                device.connectGatt(this, false, gattClientCallback, BluetoothDevice.TRANSPORT_LE)
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
                Log.d("BluetoothActivity", "Device connected: ${gatt?.device?.name}")
                // 권한 확인
                if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 있을 때 서비스 발견 시작
                    gatt?.discoverServices()
                } else {
                    // 권한이 없을 때 사용자에게 권한 요청
                    requestPermissions(
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION
                    )
                }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // 기기가 연결이 끊겼을 때
                Log.d("BluetoothActivity", "Device disconnected")
                if (status == BluetoothGatt.GATT_FAILURE || status == 133) {
                    // 연결 실패 또는 특정 오류 코드에서 재시도
                    Log.d("BluetoothActivity", "Attempting to reconnect...")
                    gatt?.close()
                    bluetoothGatt = null
                    Handler(Looper.getMainLooper()).postDelayed({
                        gatt?.device?.let { connectToDevice(it) }  // 3초 후 재시도
                    }, 3000)
                }
            }
        }

        // 서비스가 발견되었을 때 호출되는 콜백
        @RequiresApi(Build.VERSION_CODES.S)
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // 서비스가 성공적으로 발견되었을 때
                val service = gatt?.getService(SERVICE_UUID) // 해당 서비스의 UUID
                val char = service?.getCharacteristic(CHAR_UUID) // 쓰기 위한 캐릭터리스틱의 UUID

                if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 있을 때
                    // 데이터 쓰기 포맷(회사코드,명령어,토큰,머신ID,유저ID)
                    val token =
                        TokenManager.getAccessToken(this@BluetoothPickActivity) // 실제 토큰 값 가져오기
                    val companyCode =
                        TokenManager.getCompanyCode(this@BluetoothPickActivity) // 회사 코드 가져오기
                    val userName =
                        TokenManager.getUserName(this@BluetoothPickActivity) // 사용자 이름 가져오기
                    char?.setValue(
                        "$companyCode,INIT,$token,,$userName".toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                    gatt?.writeCharacteristic(char)

                    // 특성 변경 알림 등록
                    gatt?.setCharacteristicNotification(char, true)

                    // CCCD(UUID 0x2902) 설정
                    val descriptor =
                        char?.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                    descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    gatt?.writeDescriptor(descriptor)

                    // 1초 후 onCharacteristicChanged 메서드 호출
                    Handler(Looper.getMainLooper()).postDelayed({
                        onCharacteristicChanged(gatt, char)
                    }, 1000)

                } else {
                    // 권한이 없을 때 사용자에게 권한 요청
                    requestPermissions(
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION
                    )
                }
            }
        }

        // 캐릭터리스틱에 데이터가 성공적으로 쓰였을 때 호출되는 콜백
        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(
                    "BluetoothActivity1",
                    "${characteristic?.value?.toString(StandardCharsets.UTF_8)}"
                )
            }
        }

        // 캐릭터리스틱 값이 변경되었을 때 호출되는 콜백
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            // 아두이노에서 보내온 데이터 수신
            // 데이터 읽기 포맷(명령어,자물쇠Uid,머신Id,배터리잔량,유저Id)
            val receivedData = characteristic?.value?.toString(StandardCharsets.UTF_8)
            // Data received: CHECK,1DA24G10,3,0,2
            Log.d("BluetoothActivity2", "Data received: $receivedData")
            receivedData?.let {
                val dataParts = it.split(",")
                if (dataParts.size == 5) {
                    val statusCode = dataParts[0]
                    val lotoUid = dataParts[1]
                    val machineId = dataParts[2]
                    val batteryInfo = dataParts[3]
                    val userId = dataParts[4]

                    // SessionManager에 데이터 설정
                    SessionManager.lotoStatusCode = statusCode
                    SessionManager.lotoUid = lotoUid
                    SessionManager.lotoMachineId = machineId
                    SessionManager.lotoBatteryInfo = batteryInfo
                    SessionManager.lotoUserId = userId

                    Log.d("BluetoothActivity2", "Session updated with received data.")

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
