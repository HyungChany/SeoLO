package com.seolo.seolo.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ConcurrentHashMap

class BluetoothAdapter(private val context: Context) {
    private var isReceiverRegistered: Boolean = false
    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothGatt: BluetoothGatt? = null
    private val discoveredDevices = ConcurrentHashMap<String, BluetoothDevice>()
    val REQUEST_BLUETOOTH_SCAN = 100
    val REQUEST_ENABLE_BT = 101

    // Bluetooth가 지원되는지 확인
    fun isBluetoothSupported(): Boolean = bluetoothAdapter != null

    // Bluetooth가 활성화되어 있는지 확인
    fun isBluetoothEnabled(): Boolean = bluetoothAdapter?.isEnabled ?: false

    // Bluetooth 활성화를 위한 인텐트 생성
    fun createEnableBluetoothIntent(): Intent? {
        return if (!isBluetoothEnabled()) {
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        } else {
            null
        }
    }

    // Bluetooth 검색 시작
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscovery() {
        if (!checkBluetoothPermissions()) {
            requestBluetoothPermissions()
        } else {
            startDiscoveryWithPermissions()
        }
    }

    // Bluetooth 권한 확인
    @RequiresApi(Build.VERSION_CODES.S)
    fun checkBluetoothPermissions(): Boolean {
        val hasScanPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED

        val hasConnectPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED

        return hasScanPermission && hasConnectPermission
    }

    // Bluetooth 권한 요청
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestBluetoothPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (!checkBluetoothPermissions()) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.BLUETOOTH_SCAN)
            }

            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    context as Activity, permissionsToRequest.toTypedArray(), REQUEST_BLUETOOTH_SCAN
                )
            }
        }
    }

    // Bluetooth 검색 시작 (권한이 있는 경우)
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryWithPermissions() {
        if (checkBluetoothPermissions()) {
            if (!isReceiverRegistered) {
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                context.registerReceiver(receiver, filter)
                isReceiverRegistered = true
            }
            bluetoothAdapter?.startDiscovery()
        } else {
            Log.e("BluetoothAdapter", "Missing BLUETOOTH_SCAN permission for startDiscovery()")
            requestBluetoothPermissions()
        }
    }

    // 특정 기기 검색 시작
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryForSpecificDevices(
        deviceNameSubstring: String, onUpdate: (List<BluetoothDevice>) -> Unit
    ) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (it.name?.contains(deviceNameSubstring, ignoreCase = true) == true) {
                        discoveredDevices[it.address] = it
                        Log.d("BluetoothAdapter", "Filtered device found: ${it.name}")
                        onUpdate(ArrayList(discoveredDevices.values))  // 콜백 호출
                    }
                }
            }
        }, filter)
        isReceiverRegistered = true
        bluetoothAdapter?.startDiscovery()
    }

    // 필터링된 기기 목록 가져오기
    fun getFilteredDevices(): MutableList<BluetoothDevice> {
        return ArrayList(discoveredDevices.values)
    }

    // Bluetooth 스캔 중지
    @SuppressLint("MissingPermission")
    fun stopDiscovery() {
        if (bluetoothAdapter?.isDiscovering == true) {
            bluetoothAdapter?.cancelDiscovery()
        }
        cleanup()
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.BLUETOOTH_CONNECT
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val deviceName = it.name ?: "Unknown Device"
                        val deviceAddress = it.address
                        Log.d(
                            "BluetoothAdapter",
                            "Discovered BLE device: $deviceName - $deviceAddress"
                        )
                    } else {
                        Log.e(
                            "BluetoothAdapter",
                            "Missing BLUETOOTH_CONNECT permission to access device name."
                        )
                    }
                }
            }
        }
    }

    // 레지스터 리시버 정리
    fun cleanup() {
        if (isReceiverRegistered) {
            try {
                context.unregisterReceiver(receiver)
            } catch (e: IllegalArgumentException) {
                Log.e("BluetoothAdapter", "Receiver not registered: ${e.message}")
            }
            isReceiverRegistered = false
        }
    }
}
