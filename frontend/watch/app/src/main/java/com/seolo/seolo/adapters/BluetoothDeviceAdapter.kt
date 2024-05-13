package com.seolo.seolo.adapters

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seolo.seolo.R

class BluetoothDeviceAdapter(private var devices: MutableList<BluetoothDevice>) :
    RecyclerView.Adapter<BluetoothDeviceAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deviceName: TextView = view.findViewById(R.id.deviceName) ?: throw IllegalArgumentException("Device name TextView not found")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
        return DeviceViewHolder(view)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.deviceName.text = devices[position].name ?: "Unknown Device"
    }

    override fun getItemCount(): Int = devices.size

    // 장치 목록 업데이트
    fun updateDevices(newDevices: List<BluetoothDevice>) {
        devices.clear()
        devices.addAll(newDevices)
        notifyDataSetChanged()  // UI 갱신 알림
    }
}
