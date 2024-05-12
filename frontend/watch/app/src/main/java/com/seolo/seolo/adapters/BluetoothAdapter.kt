package com.seolo.seolo.adapters

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
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

/**
 * BluetoothAdapter 클래스는 Bluetooth 기능을 관리하는 데 사용됩니다.
 * Bluetooth 기기 검색 및 연결, 권한 확인 등의 기능을 제공합니다.
 *
 * @property context 컨텍스트
 * @constructor BluetoothAdapter 인스턴스를 생성합니다.
 */
class BluetoothAdapter(private val context: Context) {
    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val REQUEST_BLUETOOTH_SCAN = 100
    val REQUEST_ENABLE_BT = 101


    // Bluetooth가 지원되는지 여부를 반환
    fun isBluetoothSupported(): Boolean = bluetoothAdapter != null


    // Bluetooth가 활성화되어 있는지 여부를 반환
    fun isBluetoothEnabled(): Boolean = bluetoothAdapter?.isEnabled ?: false

    // Bluetooth를 활성화하기 위한 인텐트를 생성하고, 이미 활성화 되어있으면 null 반환
    fun createEnableBluetoothIntent(): Intent? {
        return if (!isBluetoothEnabled()) {
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        } else {
            null
        }
    }

    // Bluetooth 기기 검색 시작
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscovery() {
        checkBluetoothPermissions()
    }

    //Bluetooth 스캔을 위한 권한을 확인하고 요청
    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkBluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                REQUEST_BLUETOOTH_SCAN
            )
        } else {
            startDiscoveryWithPermissions()
        }
    }

    // Bluetooth 스캔을 권한이 부여된 상태에서 시작
    private fun startDiscoveryWithPermissions() {
        // 블루투스 스캔 권한 확인
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)
            bluetoothAdapter?.startDiscovery()
        } else {
            // 권한이 없는 경우, 로깅하거나 사용자에게 피드백 제공
            Log.e("BluetoothAdapter", "Missing BLUETOOTH_SCAN permission for startDiscovery()")
            // 필요하다면 사용자에게 권한 요청
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                REQUEST_BLUETOOTH_SCAN
            )
        }
    }

    // 블루투스 디바이스 검색 결과를 처리하는 BroadcastReceiver
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    // 권한 체크 후 장치 이름 접근
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.BLUETOOTH_CONNECT
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val deviceName = it.name ?: "Unknown Device"
                        val deviceAddress = it.address
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

    // BroadcastReceiver를 등록 해제
    fun cleanup() {
        context.unregisterReceiver(receiver)
    }
}
