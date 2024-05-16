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

@Parcelize
data class IssueResponse(
    val next_code: String,
    val token_value: String,
    val task_history: String?,
    val check_more_response: String?,
    val http_status: String,
    val message: String?
) : Parcelable

@Parcelize
data class LotoInfo(
    val locker_uid: String,
    val battery_info: String,
    val machine_id: String,
    val task_template_id: String,
    val task_precaution: String,
    val end_time: String
) : Parcelable

@Parcelize
data class LotoUnlockInfo(
    val locker_uid: String,
    val battery_info: String,
    val machine_id: String,
    val token_value: String,
) : Parcelable


@Parcelize
data class UnlockResponse(
    val next_code: String,
    val token_value: String,
    val task_history: String?,
    val check_more_response: String?,
    val http_status: String,
    val message: String?
) : Parcelable