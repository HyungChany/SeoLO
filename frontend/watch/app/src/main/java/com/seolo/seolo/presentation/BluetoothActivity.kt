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

class BluetoothActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    companion object {
        private const val REQUEST_BLUETOOTH_PERMISSION = 101
        private val SERVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        private val CHAR_UUID = UUID.fromString("00001102-0000-1000-8000-00805F9B34FB")

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_layout)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.bluetoothDeviceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        deviceAdapter = BluetoothDeviceAdapter(devices) { device ->
            connectToDevice(device)
        }
        recyclerView.adapter = deviceAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        bluetoothAdapter = BluetoothAdapter(this)

        if (!bluetoothAdapter.checkBluetoothPermissions()) {
            bluetoothAdapter.requestBluetoothPermissions()
        } else {
            bluetoothAdapter.startDiscoveryForSpecificDevices("") { newDevices ->
                deviceAdapter.updateDevices(newDevices)
            }
        }
    }

//    private fun connectToDevice(device: BluetoothDevice) {
//        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
//            val deviceName = device.name ?: "Unknown Device"
//            Toast.makeText(this@BluetoothActivity, "${deviceName} 클릭", Toast.LENGTH_SHORT).show()
//        } else {
//            // 권한이 없으면 사용자에게 권한 요청
//            requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION)
//        }
//    }


    // 기기 연결 및 데이터 전송 로직을 포함한 함수
    private fun connectToDevice(device: BluetoothDevice) {
        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            val deviceName = device.name ?: "Unknown Device"
            Toast.makeText(this@BluetoothActivity, "$deviceName 클릭", Toast.LENGTH_SHORT).show()

            // Bluetooth GATT로 기기 연결 시작
            device.connectGatt(this, false, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                    super.onConnectionStateChange(gatt, status, newState)
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        Log.d("BluetoothActivity", "Device connected: $deviceName")
                        gatt?.discoverServices() // 서비스 발견 시작
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        Log.d("BluetoothActivity", "Device disconnected")
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                    super.onServicesDiscovered(gatt, status)
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        // 적절한 서비스와 캐릭터리스틱 찾기
                        val service = gatt?.getService(SERVICE_UUID) // 해당 서비스의 UUID
                        val char = service?.getCharacteristic(CHAR_UUID) // 쓰기 위한 캐릭터리스틱의 UUID

                        // "통신보안" 문자열을 캐릭터리스틱에 쓰기
                        char?.setValue("통신보안")
                        gatt?.writeCharacteristic(char)
                    }
                }

                override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: android.bluetooth.BluetoothGattCharacteristic?, status: Int) {
                    super.onCharacteristicWrite(gatt, characteristic, status)
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        Log.d("BluetoothActivity", "Data written to ${characteristic?.uuid}: ${characteristic?.value}")
                    }
                }
            })
        } else {
            // 권한이 없으면 사용자에게 권한 요청
            requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION)
        }
    }



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

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == bluetoothAdapter.REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            bluetoothAdapter.startDiscovery()
        }
    }

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

    override fun onDestroy() {
        bluetoothAdapter.cleanup()
        super.onDestroy()
    }
}