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

    fun isBluetoothSupported(): Boolean = bluetoothAdapter != null

    fun isBluetoothEnabled(): Boolean = bluetoothAdapter?.isEnabled ?: false

    fun createEnableBluetoothIntent(): Intent? {
        return if (!isBluetoothEnabled()) {
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        } else {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscovery() {
        if (!checkBluetoothPermissions()) {
            requestBluetoothPermissions()
        } else {
            startDiscoveryWithPermissions()
        }
    }

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

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryWithPermissions() {
        if (checkBluetoothPermissions()) {
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)
            bluetoothAdapter?.startDiscovery()
        } else {
            Log.e("BluetoothAdapter", "Missing BLUETOOTH_SCAN permission for startDiscovery()")
            requestBluetoothPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startDiscoveryForSpecificDevices(
        deviceNameSubstring: String, onUpdate: (List<BluetoothDevice>) -> Unit
    ) {
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
        }, IntentFilter(BluetoothDevice.ACTION_FOUND))
        bluetoothAdapter?.startDiscovery()
    }

    fun getFilteredDevices(): MutableList<BluetoothDevice> {
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

    fun cleanup() {
        if (isReceiverRegistered) {
            context.unregisterReceiver(receiver)
            isReceiverRegistered = false
        }
    }
}
