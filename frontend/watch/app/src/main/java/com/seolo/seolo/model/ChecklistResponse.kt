package com.seolo.seolo.model

import com.google.gson.annotations.SerializedName

data class ChecklistResponse(
    @SerializedName("basic_checklists") val basicChecklists: List<ChecklistItem>

)

data class ChecklistItem(
    val id: Int,
    val context: String,
)
