package com.seolo.seolo.presentation

import android.Manifest
import android.bluetooth.BluetoothDevice
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

class BluetoothActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothDeviceAdapter
    private var devices = mutableListOf<BluetoothDevice>()
    companion object {
        private const val REQUEST_BLUETOOTH_PERMISSION = 101
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

    private fun connectToDevice(device: BluetoothDevice) {
        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            val deviceName = device.name ?: "Unknown Device"
            Toast.makeText(this@BluetoothActivity, "${deviceName} 클릭", Toast.LENGTH_SHORT).show()
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