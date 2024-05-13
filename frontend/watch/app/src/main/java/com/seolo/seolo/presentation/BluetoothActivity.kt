package com.seolo.seolo.presentation

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R
import com.seolo.seolo.adapters.BluetoothAdapter
import com.seolo.seolo.adapters.BluetoothDeviceAdapter

class BluetoothActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.bluetooth_layout)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.bluetoothDeviceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val devices = mutableListOf<BluetoothDevice>()
        deviceAdapter = BluetoothDeviceAdapter(devices) { device ->
            connectToDevice(device)  // Handle the device click
        }
        recyclerView.adapter = deviceAdapter


        // SnapHelper를 설정합니다.
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        bluetoothAdapter = BluetoothAdapter(this)
        bluetoothAdapter.startDiscoveryForSpecificDevices("") { newDevices ->
            deviceAdapter.updateDevices(newDevices)
        }
    }

    private fun connectToDevice(device: BluetoothDevice) {
        Log.d("BluetoothActivity", "Connecting to ${device.name}")
        bluetoothAdapter.connectToDevice(device)
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