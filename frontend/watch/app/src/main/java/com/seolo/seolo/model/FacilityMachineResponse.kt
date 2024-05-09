package com.seolo.seolo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class FacilityResponse(
    val facilities: List<FacilityItem>
)

@Parcelize
data class FacilityItem(
    val id: Int,
    val name: String,
) : Parcelable

data class MachineResponse(
    val machines: List<MachineItem>
)

@Parcelize
data class MachineItem(
    @SerializedName("machine_id") val machineId: Int,
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("facility_name") val facilityName: String,
    @SerializedName("machine_name") val machineName: String,
    @SerializedName("machine_code") val machineCode: String,
    @SerializedName("introduction_date") val introductionDate: String,
    @SerializedName("main_manager_id") val mainManagerId: Int?,
    @SerializedName("main_manager_name") val mainManagerName: String?,
    @SerializedName("sub_manager_id") val subManagerId: Int?,
    @SerializedName("sub_manager_name") val subManagerName: String?
) : Parcelable
