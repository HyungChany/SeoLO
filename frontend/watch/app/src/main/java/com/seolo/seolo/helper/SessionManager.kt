package com.seolo.seolo.helper

object SessionManager {
    var selectedFacilityName: String? = null
    var selectedMachineId: String? = null
    var selectedTaskTemplateId: String? = null
    var selectedTaskPrecaution: String? = null
    var selectedDate: String? = null
    var selectedTime: String? = null

    fun clear() {
        selectedFacilityName = null
        selectedMachineId = null
        selectedTaskTemplateId = null
        selectedTaskPrecaution = null
        selectedDate = null
        selectedTime = null
    }
}
