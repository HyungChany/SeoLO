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
import android.view.View
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
import com.seolo.seolo.helper.SessionManager
import com.seolo.seolo.helper.TokenManager
import com.seolo.seolo.model.IssueResponse
import com.seolo.seolo.model.LockedInfo
import com.seolo.seolo.model.LockedResponse
import com.seolo.seolo.model.LotoInfo
import com.seolo.seolo.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.util.UUID

class BluetoothLOTOActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null
    private var lastSentData: String? = null
    private var selectedDevice: BluetoothDevice? = null
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
        selectedDevice = device

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
            Toast.makeText(this@BluetoothLOTOActivity, "$deviceName 클릭", Toast.LENGTH_SHORT).show()

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
            Log.d("블루투스 연결 상태 변경_LOTO", "Status: $status, New State: $newState")
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 기기가 연결될 때
                Log.d("블루투스 연결_LOTO", "${gatt?.device?.name}")
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
                Log.d("블루투스 연결 해제_LOTO", "블루투스 연결 해제")
                if (status == BluetoothGatt.GATT_FAILURE || status == 133) {
                    // 연결 실패 또는 특정 오류 코드에서 재시도
                    Log.d("블루투스 연결 재시도_LOTO", "블루투스 연결 재 시도")
                    gatt?.close()
                    bluetoothGatt = null
                    Handler(Looper.getMainLooper()).postDelayed({
                        gatt?.device?.let { connectToDevice(it) }
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
                    val companyCode = TokenManager.getCompanyCode(this@BluetoothLOTOActivity)
                    val token = TokenManager.getTokenValue(this@BluetoothLOTOActivity)
                    val machineId = LotoManager.getLotoMachineId(this@BluetoothLOTOActivity)
                    val userId = TokenManager.getUserId(this@BluetoothLOTOActivity)
                    val lotoUid = LotoManager.getLotoUid(this@BluetoothLOTOActivity)
                    val sendData = "$companyCode,$token,$machineId,$userId,$lotoUid,INIT"
                    lastSentData = sendData
                    Log.d("데이터 쓰기_LOTO", sendData)
                    char?.setValue(sendData.toByteArray(StandardCharsets.UTF_8))
                    gatt?.writeCharacteristic(char)

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
                    "데이터 쓰기 성공_LOTO", "${characteristic?.value?.toString(StandardCharsets.UTF_8)}"
                )
            } else {
                Log.e("데이터 쓰기 실패_LOTO", "Status: $status")
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
            // 데이터 읽기 포맷(명령어,자물쇠uid,머신id,배터리잔량,유저id)
            val receivedData = characteristic?.value?.toString(StandardCharsets.UTF_8)

            // 송신 데이터와 수신 데이터가 같으면 리턴
            if (receivedData == lastSentData) return

            Log.d("수신데이터_LOTO", "$receivedData")

            receivedData?.let {
                val dataParts = it.split(",")
                val lotoUserId = TokenManager.getUserId(this@BluetoothLOTOActivity)

                if (dataParts.size >= 4 && (lotoUserId != null)) {
                    statusCode = dataParts[0]
                    val lotoUid = dataParts[1]
                    val machineId = dataParts[2]
                    val batteryInfo = dataParts[3]

                    // LotoManager에 데이터 설정
                    LotoManager.setLotoStatusCode(this@BluetoothLOTOActivity, statusCode)
                    LotoManager.setLotoUid(this@BluetoothLOTOActivity, lotoUid)
                    LotoManager.setLotoMachineId(this@BluetoothLOTOActivity, machineId)
                    LotoManager.setLotoBatteryInfo(this@BluetoothLOTOActivity, batteryInfo)
                    LotoManager.setLotoUserId(this@BluetoothLOTOActivity, lotoUserId)
                    Log.d("수신데이터_LOTO체크", "statusCode: $statusCode, lotoUid: $lotoUid")
                    if (statusCode == "WRITED") {
                        // WRITE 상태인 경우 API 호출
                        issueCoreLogic {
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    this@BluetoothLOTOActivity,
                                    "$statusCode, 잠금완료",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = Intent(this@BluetoothLOTOActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }, 1000)
                            }
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this@BluetoothLOTOActivity,
                                "$statusCode ,이미 잠겨져있는 자물쇠입니다. 다른 자물쇠를 선택하세요. \n 배터리 잔량: $batteryInfo",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    // 데이터 형식이 올바르지 않을 경우 로그 및 오류 처리
                    Log.e("수신데이터 오류_LOTO", "수신된 데이터 형식이 올바르지 않습니다: $receivedData")
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@BluetoothLOTOActivity,
                            "수신된 데이터 형식이 올바르지 않습니다: $receivedData",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    // ISSUE API 요청 함수
    private fun issueCoreLogic(onCompleted: () -> Unit) {
        val authorization = "Bearer " + TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val deviceType = "watch"

        val lotoInfo = LotoManager.getLotoUid(this@BluetoothLOTOActivity)?.let {
            LotoManager.getLotoBatteryInfo(this@BluetoothLOTOActivity)?.let { batteryInfo ->
                LotoInfo(
                    locker_uid = it,
                    battery_info = batteryInfo,
                    machine_id = SessionManager.selectedMachineId ?: "",
                    task_template_id = SessionManager.selectedTaskTemplateId ?: "",
                    task_precaution = SessionManager.selectedTaskPrecaution ?: "",
                    end_time = SessionManager.selectedDate + SessionManager.selectedTime
                )
            }
        }

        val call = lotoInfo?.let {
            RetrofitClient.issueService.sendLotoInfo(
                authorization = authorization,
                companyCode = companyCode ?: "",
                deviceType = deviceType,
                lotoInfo = it
            )
        }

        // Debug logs to check the data before making the API call
        Log.d("API_CALL_ISSUE", "Authorization: $authorization")
        Log.d("API_CALL_ISSUE", "CompanyCode: $companyCode")
        Log.d("API_CALL_ISSUE", "DeviceType: $deviceType")
        Log.d("API_CALL_ISSUE", "LotoInfo: $lotoInfo")

        call?.enqueue(object : Callback<IssueResponse> {
            override fun onResponse(call: Call<IssueResponse>, response: Response<IssueResponse>) {
                if (response.isSuccessful) {
                    val issueResponse = response.body()
                    Log.d("API_CALL_ISSUE", "Response Success: ${issueResponse?.next_code}")
                    val nextCode = issueResponse?.next_code
                    // 응답에서 token_value 저장
                    issueResponse?.token_value?.let {
                        TokenManager.setTokenValue(this@BluetoothLOTOActivity, it)
                    }
                    Log.d("API_CALL_ISSUE", "response.body(): $issueResponse")
                    Log.d(
                        "API_CALL_ISSUE",
                        "response.body()?.token_value: ${issueResponse?.token_value}"
                    )
                    Log.d(
                        "API_CALL_ISSUE", "response.body()?.next_code: ${issueResponse?.next_code}"
                    )

                    // next_code를 사용하여 Bluetooth 데이터 전송
                    if (nextCode != null) {
                        sendBluetoothData(nextCode)
                    }

                    // onCompleted 콜백 호출
                    onCompleted()
                } else {
                    Log.d("API_CALL_ISSUE", "Response Failed: ${response.message()}")
//                    Toast.makeText(
//                        this@BluetoothLOTOActivity,
//                        "Failed: ${response.message()}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }

            override fun onFailure(call: Call<IssueResponse>, t: Throwable) {
                Log.d("API_CALL_ISSUE", "Error: ${t.message}")
                Toast.makeText(
                    this@BluetoothLOTOActivity, "Error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // BE 응답의 next_code를 블루투스로 송신하는 함수
    private fun sendBluetoothData(nextCode: String) {
        selectedDevice?.let { device ->
            bluetoothGatt?.let { gatt ->
                if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    val service = gatt.getService(SERVICE_UUID)
                    val char = service?.getCharacteristic(CHAR_UUID)
                    if (char != null) {
                        val companyCode = TokenManager.getCompanyCode(this)
                        val token = TokenManager.getTokenValue(this)
                        val machineId = SessionManager.selectedMachineId
                        val userId = TokenManager.getUserId(this)
                        val lotoUid = LotoManager.getLotoUid(this)
                        val sendData = "$companyCode,$token,$machineId,$userId,$lotoUid,$nextCode"
                        lastSentData = sendData
                        char.setValue(sendData.toByteArray(StandardCharsets.UTF_8))

                        // 데이터 전송 로그 추가
                        Log.d("sendBluetoothData", "Sending data: $sendData")
                        val success = gatt.writeCharacteristic(char)
                        Log.d("sendBluetoothData", "writeCharacteristic success: $success")

                        // 데이터 전송이 실패할 경우 처리
                        if (!success) {
                            Log.e("sendBluetoothData", "Failed to send data")
                            Toast.makeText(this, "블루투스 데이터 전송 실패", Toast.LENGTH_SHORT).show()
                        } else {
                            isDataReceived = false
                            lockedCoreLogic {
                                Log.d("sendBluetoothData", "Locked API called")
                            }
                            Log.d("sendBluetoothData", "Data sent successfully")
                        }
                    } else {
                        Log.e("sendBluetoothData", "Characteristic is null")
                    }
                } else {
                    // 권한이 없는 경우 사용자에게 권한 요청 또는 예외 처리
                    requestPermissions(
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION
                    )
                }
            }
        }
    }

    // LOCKED API 요청 함수
    private fun lockedCoreLogic(onCompleted: () -> Unit) {
        val authorization = "Bearer " + TokenManager.getAccessToken(this)
        val companyCode = TokenManager.getCompanyCode(this)
        val deviceType = "watch"

        val lotoInfo = LotoManager.getLotoUid(this@BluetoothLOTOActivity)?.let { uid ->
            LotoManager.getLotoBatteryInfo(this@BluetoothLOTOActivity)?.let { batteryInfo ->
                LockedInfo(
                    locker_uid = uid,
                    battery_info = batteryInfo,
                    machine_id = SessionManager.selectedMachineId ?: ""
                )
            }
        }

        val call = lotoInfo?.let {
            RetrofitClient.lockedService.sendLockedInfo(
                authorization = authorization,
                companyCode = companyCode ?: "",
                deviceType = deviceType,
                lockedInfo = it
            )
        }

        // Debug logs to check the data before making the API call
        Log.d("API_CALL_LOCKED", "Authorization: $authorization")
        Log.d("API_CALL_LOCKED", "CompanyCode: $companyCode")
        Log.d("API_CALL_LOCKED", "DeviceType: $deviceType")
        Log.d("API_CALL_LOCKED", "LotoInfo: $lotoInfo")

        call?.enqueue(object : Callback<LockedResponse> {
            override fun onResponse(
                call: Call<LockedResponse>, response: Response<LockedResponse>
            ) {
                if (response.isSuccessful) {
                    val issueResponse = response.body()
                    Log.d("API_CALL_LOCKED", "response.body(): $issueResponse")
                    Log.d("API_CALL_LOCKED", "Response Success: ${issueResponse?.next_code}")
                    val nextCode = issueResponse?.next_code
                    Log.d(
                        "API_CALL_LOCKED", "response.body()?.next_code: ${issueResponse?.next_code}"
                    )
                    val message = issueResponse?.message

                    // onCompleted 콜백 호출
                    onCompleted()
                } else {
                    Log.d("API_CALL_LOCKED", "Response Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LockedResponse>, t: Throwable) {
                Log.d("API_CALL_LOCKED", "Error: ${t.message}")
                Toast.makeText(
                    this@BluetoothLOTOActivity, "Error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
                // onCompleted 콜백 호출
                onCompleted()
            }
        }) ?: onCompleted() // call이 null인 경우에도 onCompleted 콜백 호출
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
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
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
            Log.e("BluetoothLOTOActivity", "권한 요청 거부") // 권한 요청 거부 처리
        }
    }

    // 액티비티 종료 시 BluetoothAdapter 정리
    override fun onDestroy() {
        try {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothGatt?.close() // 권한이 있을 때 GATT 연결 해제
            }
        } catch (e: SecurityException) {
            Log.e("BluetoothLOTOActivity", "권한 에러: ${e.message}")
        } finally {
            bluetoothAdapter.cleanup()
            super.onDestroy()
        }
    }
}
