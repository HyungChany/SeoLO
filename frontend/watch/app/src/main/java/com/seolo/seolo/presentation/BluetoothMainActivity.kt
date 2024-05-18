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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R
import com.seolo.seolo.adapters.BluetoothAdapter
import com.seolo.seolo.adapters.BluetoothDeviceAdapter
import com.seolo.seolo.helper.LotoManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.UnlockInfo
import com.seolo.seolo.model.UnlockResponse
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.util.UUID

class BluetoothMainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null
    private var lastSentData: String? = null
    private var isDataReceived = false
    private var statusCode: String = "INIT"


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

        // 최상위 레이아웃에 클릭 리스너 추가
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        mainLayout.setOnClickListener {
            // 블루투스 재탐색 시작
            if (!bluetoothAdapter.checkBluetoothPermissions()) {
                bluetoothAdapter.requestBluetoothPermissions()
            } else {
                bluetoothAdapter.startDiscoveryForSpecificDevices("SEOLO LOCK") { newDevices ->
                    deviceAdapter.updateDevices(newDevices)
                }
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
            Toast.makeText(this@BluetoothMainActivity, "$deviceName 와 연결중입니다.", Toast.LENGTH_LONG)
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
            Log.d("블루투스 연결 상태 변경_Main", "Status: $status, New State: $newState")
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 기기가 연결될 때
                Log.d("블루투스 연결_Main", "${gatt?.device?.name}")
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
                Log.d("블루투스 연결 해제_Main", "블루투스 연결 해제")
                if (status == BluetoothGatt.GATT_FAILURE || status == 133) {
                    // 연결 실패 또는 특정 오류 코드에서 재시도
                    Log.d("블루투스 연결 재 시도_Main", "블루투스 연결 재 시도")
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
                    // 데이터 쓰기 포맷(회사코드,토큰,머신ID,유저ID,자물쇠UID,명령어)
                    val companyCode =
                        TokenManager.getCompanyCode(this@BluetoothMainActivity) // 회사 코드 가져오기
                    val token =
                        TokenManager.getTokenValue(this@BluetoothMainActivity) // 자물쇠 토큰 값 가져오기
                    val machineId =
                        LotoManager.getLotoMachineId(this@BluetoothMainActivity) // 머신 Id 가져오기
                    val userId = TokenManager.getUserId(this@BluetoothMainActivity) // 사용자 Id 가져오기
                    val lotoUid = LotoManager.getLotoUid(this@BluetoothMainActivity) // 자물쇠 Uid 가져오기

                    val sendData = if (lotoUid == "") {
                        "$companyCode,$token,$machineId,$userId,$lotoUid,INIT"
                    } else {
                        "$companyCode,$token,$machineId,$userId,$lotoUid,LOCKED"
                    }
                    lastSentData = sendData
                    char?.setValue(sendData.toByteArray(StandardCharsets.UTF_8))
                    gatt?.writeCharacteristic(char)

                    Log.d("송신데이터_Main", sendData)

                    // 특성 변경 알림 등록
                    gatt?.setCharacteristicNotification(char, true)

                    // CCCD(UUID 0x2902) 설정
                    val descriptor =
                        char?.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                    descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    gatt?.writeDescriptor(descriptor)

                    // 5초 후 onCharacteristicChanged 메서드 호출
                    Handler(Looper.getMainLooper()).postDelayed({
                        onCharacteristicChanged(gatt, char)
                    }, 5000)

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
                    "데이터 읽기 쓰기 성공_Main", "${characteristic?.value?.toString(StandardCharsets.UTF_8)}"
                )
            }
        }

        // 캐릭터리스틱 값이 변경되었을 때 호출되는 콜백
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)

            // 데이터가 이미 수신되었으면 무시
            if (isDataReceived) return


            // 데이터 수신 상태 플래그 설정
            isDataReceived = true

            // 아두이노에서 보내온 데이터 수신
            // 데이터 읽기 포맷(명령어,자물쇠Uid,머신Id,배터리잔량,유저Id)
            val receivedData = characteristic?.value?.toString(StandardCharsets.UTF_8)
            val lotoUserId = TokenManager.getUserId(this@BluetoothMainActivity)
            // 송신 데이터와 수신 데이터가 같으면 리턴
            if (receivedData == lastSentData) return
            Log.d("수신데이터_Main", "Data received: $receivedData")

            receivedData?.let {
                val dataParts = it.split(",")
                if (dataParts.size >= 4) {
                    statusCode = dataParts[0]
                    val lotoUid = dataParts[1]
                    val machineId = dataParts[2]
                    val batteryInfo = dataParts[3]
                    // 자물쇠 상태 확인 명령어가 CHECK일 때(자물쇠가 잠겨있는데 그냥 일단 찍어본 경우)
                    Log.d("체크", "$statusCode, $lotoUid, $machineId, $batteryInfo, $lotoUserId")
                    if (statusCode == "CHECK") {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this@BluetoothMainActivity,
                                "내가 잠근 자물쇠가 아닙니다. 잠금을 해제할 수 없습니다. \n 배터리 잔량: $batteryInfo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (statusCode == "WRITE") {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this@BluetoothMainActivity,
                                "LOTO 절차를 시작하겠습니다. \n 배터리 잔량: $batteryInfo",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(this@BluetoothMainActivity, ChecklistActivity::class.java)
                            startActivity(intent)
                        }
                    } else if (statusCode == "ALERT") {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this@BluetoothMainActivity,
                                "2개 이상의 자물쇠를 잠굴 수 없습니다! \n 배터리 잔량: $batteryInfo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (statusCode == "UNLOCK") {
                        Log.d("수신데이터_Main", "UNLOCK 상태 수신됨")
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this@BluetoothMainActivity, "잠금 해제 요청을 처리합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        unlockCoreLogic {
                            Log.d("수신데이터_Main", "unlockCoreLogic 호출됨")
                            LotoManager.clearLoto(this@BluetoothMainActivity)
                            Handler(Looper.getMainLooper()).post {
                                val intent =
                                    Intent(this@BluetoothMainActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    // API 요청 함수
    private fun unlockCoreLogic(onCompleted: () -> Unit) {
        val authorization = "Bearer " + TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val deviceType = "watch"

        val unlockInfo = LotoManager.getLotoUid(this@BluetoothMainActivity)?.let { uid ->
            LotoManager.getLotoBatteryInfo(this@BluetoothMainActivity)?.let { batteryInfo ->
                LotoManager.getLotoMachineId(this@BluetoothMainActivity)?.let { machineId ->
                    TokenManager.getTokenValue(this@BluetoothMainActivity)?.let { tokenValue ->
                        UnlockInfo(
                            uid, batteryInfo, machineId, tokenValue
                        )
                    }
                }
            }
        }
        Log.d("unlock call 직전", "언락 콜 직전")
        val call = unlockInfo?.let {
            RetrofitClient.unlockService.sendUnlockInfo(
                authorization = authorization,
                companyCode = companyCode ?: "",
                deviceType = deviceType,
                unlockInfo = it
            )
        }
        Log.d("unlock 응답 직전", "언락 응답 직전")
        call?.enqueue(object : Callback<UnlockResponse> {
            override fun onResponse(
                call: Call<UnlockResponse>, response: Response<UnlockResponse>
            ) {
                if (response.isSuccessful) {
                    val unlockResponse = response.body()
                    val message = unlockResponse?.message
                    Log.d("API 요청 성공_Main", "API 요청 성공: $message")
                    runOnUiThread {
                        Toast.makeText(
                            this@BluetoothMainActivity, "자물쇠 잠금이 해제 됐습니다.", Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorMessage = response.message()
                    Log.d("API 요청 실패_Main", "API 요청 실패: $errorMessage")
                    runOnUiThread {
                        Toast.makeText(
                            this@BluetoothMainActivity, errorMessage, Toast.LENGTH_LONG
                        ).show()
                    }
                }
                onCompleted()
            }

            override fun onFailure(call: Call<UnlockResponse>, t: Throwable) {
                Log.d("API 요청 실패_Main", "Error: ${t.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@BluetoothMainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT
                    ).show()
                }
                onCompleted()
            }
        })
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
            Log.e("BluetoothMainActivity", "권한 요청 거부 처리")
        }
    }

    // 액티비티 종료 시 BluetoothAdapter 정리
    override fun onDestroy() {
        try {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothGatt?.close() // 권한이 있을 때 GATT 연결 해제
            }
        } catch (e: SecurityException) {
            Log.e("BluetoothMainActivity", "권한 에러: ${e.message}")
        } finally {
            bluetoothAdapter.cleanup()
            super.onDestroy()
        }
    }
}
