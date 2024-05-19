package com.seolo.seolo.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R

class BluetoothDeviceAdapter(
    private val context: Context,
    private var devices: MutableList<BluetoothDevice>,
    private val onClick: (BluetoothDevice) -> Unit
) : RecyclerView.Adapter<BluetoothDeviceAdapter.DeviceViewHolder>() {

    private val gattMap = mutableMapOf<String, BluetoothGatt?>()


    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 뷰 요소 초기화
        val deviceName: TextView = view.findViewById(R.id.deviceName)
            ?: throw IllegalArgumentException("Device name TextView not found")
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        // 뷰 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bluetooth_device, parent, false)
        return DeviceViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        // 장치 정보 가져오기
        val device = devices[position]
        // ViewHolder에 데이터 설정
        holder.deviceName.text = device.name ?: "Unknown Device"
        // 클릭 이벤트 설정
        holder.itemView.setOnClickListener {
            // 클릭 시 콜백 함수 호출
            onClick(device)
        }
    }

    // 데이터 개수 반환
    override fun getItemCount(): Int = devices.size

    // 장치 목록 업데이트
    fun updateDevices(newDevices: List<BluetoothDevice>) {
        // 기존 목록 지우고 새로운 목록 추가
        devices.clear()
        devices.addAll(newDevices)
        // 데이터 변경 알림
        notifyDataSetChanged()
    }

    fun cleanupGatt() {
        gattMap.values.forEach { gatt ->
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                gatt?.disconnect()
                gatt?.close()
            }
        }
        gattMap.clear()
    }

}
