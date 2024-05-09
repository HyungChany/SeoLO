package com.seolo.seolo.model

import com.google.gson.annotations.SerializedName

data class FacilityResponse(
    val facilities: List<FacilityItem>
)

data class FacilityItem(
    val id: Int,
    val name: String,
)

data class MachineResponse(
    val machines: List<MachineItem>
)

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
)
