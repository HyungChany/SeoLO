package com.seolo.seolo.model

data class ChecklistResponse(
    val checklists: List<ChecklistItem>
)

data class ChecklistItem(
    val id: Int,
    val context: String,
)