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
    @SerializedName("machine_id_name_list") val machines: List<MachineItem>
)

@Parcelize
data class MachineItem(
    @SerializedName("machine_id") val machineId: Int,
    @SerializedName("machine_name") val machineName: String,
) : Parcelable


data class TaskResponse(
    @SerializedName("templates") val tasks: List<TaskItem>
)


@Parcelize
data class TaskItem(
    @SerializedName("task_template_id") val taskTemplateId: Int,
    @SerializedName("task_template_name") val taskTemplateName: String,
    @SerializedName("task_precaution") val taskPrecaution: String,
) : Parcelable