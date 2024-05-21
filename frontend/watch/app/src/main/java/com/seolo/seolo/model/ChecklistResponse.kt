package com.seolo.seolo.model

import com.google.gson.annotations.SerializedName

data class ChecklistResponse(
    @SerializedName("basic_checklists") val basicChecklists: List<BasicChecklistItem>,
    @SerializedName("checklists") val checklists: List<ChecklistTextItem>

)
interface ChecklistItem {
    val id: Int
    val context: String
}

data class BasicChecklistItem(
    override val id: Int,
    override val context: String
) : ChecklistItem

data class ChecklistTextItem(
    override val id: Int,
    override val context: String
) : ChecklistItem
