package com.seolo.seolo.helper

object SessionManager {
    var selectedFacilityName: String? = null
    var selectedMachineId: String? = null
    var selectedMachineName: String? = null
    var selectedTaskTemplateId: String? = null
    var selectedTaskPrecaution: String? = null
    var selectedDate: String? = null
    var selectedTime: String? = null
    var selectedSimpleDate: String? = null
    var selectedSimpleTime: String? = null
    fun clear() {
        selectedFacilityName = null
        selectedMachineName = null
        selectedMachineId = null
        selectedTaskTemplateId = null
        selectedTaskPrecaution = null
        selectedDate = null
        selectedTime = null
        selectedSimpleDate = null
        selectedSimpleTime = null
    }
}
