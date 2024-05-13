package com.seolo.seolo.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
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
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * BluetoothAdapter 클래스는 Bluetooth 기능을 관리하는 데 사용됩니다.
 * Bluetooth 기기 검색 및 연결, 권한 확인 등의 기능을 제공합니다.
 *
 * @property context 컨텍스트
 * @constructor BluetoothAdapter 인스턴스를 생성합니다.
 */
class BluetoothAdapter(private val context: Context) {
    private var isReceiverRegistered: Boolean = false
    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val discoveredDevices = ConcurrentHashMap<String, BluetoothDevice>()
    val REQUEST_BLUETOOTH_SCAN = 100
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

    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscovery() {
        checkBluetoothPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkBluetoothPermissions() {
        val requiredPermissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(Manifest.permission.BLUETOOTH_SCAN)
        }

        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (requiredPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                context as Activity, requiredPermissions.toTypedArray(), REQUEST_BLUETOOTH_SCAN
            )
        } else {
            startDiscoveryWithPermissions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryWithPermissions() {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)
            bluetoothAdapter?.startDiscovery()
        } else {
            Log.e("BluetoothAdapter", "Missing BLUETOOTH_SCAN permission for startDiscovery()")
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                REQUEST_BLUETOOTH_SCAN
            )
        }
    }
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryForSpecificDevices(deviceNameSubstring: String, onUpdate: (List<BluetoothDevice>) -> Unit) {
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (it.name?.contains(deviceNameSubstring, ignoreCase = true) == true) {
                        discoveredDevices[it.address] = it
                        Log.d("BluetoothAdapter", "Filtered device found: ${it.name}")
                        onUpdate(ArrayList(discoveredDevices.values))  // 콜백 호출
                    }
                }
            }
        }, IntentFilter(BluetoothDevice.ACTION_FOUND))
        bluetoothAdapter?.startDiscovery()
    }

    fun getFilteredDevices(): List<BluetoothDevice> {
        return ArrayList(discoveredDevices.values)
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
                        Log.d("BluetoothAdapter", "Discovered BLE device: $deviceName - $deviceAddress")
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

    fun sendData(device: BluetoothDevice, data: ByteArray) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.e("BluetoothAdapter", "BLUETOOTH_CONNECT permission not granted")
            return
        }

        @SuppressLint("MissingPermission")
        val gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt?.discoverServices()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                val service = gatt?.getService(UUID.fromString("your-service-uuid"))
                val characteristic =
                    service?.getCharacteristic(UUID.fromString("your-characteristic-uuid"))
                characteristic?.value = data
                characteristic?.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    gatt?.writeCharacteristic(characteristic)
                } else {
                    Log.e("BluetoothAdapter", "BLUETOOTH_CONNECT permission not granted during write")
                }
            }

            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
            ) {
                super.onCharacteristicWrite(gatt, characteristic, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d("BluetoothAdapter", "Data sent successfully")
                }
            }
        })
    }


    fun cleanup() {
        if (isReceiverRegistered) {
            context.unregisterReceiver(receiver)
            isReceiverRegistered = false
        }
    }
}
