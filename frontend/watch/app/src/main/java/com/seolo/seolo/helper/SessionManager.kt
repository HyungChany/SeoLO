package com.seolo.seolo.helper

object SessionManager {
    var selectedBatteryInfo: String? = null
    var selectedFacilityName: String? = null
    var selectedMachineId: String? = null
    var selectedMachineName: String? = null
    var selectedTaskTemplateId: String? = null
    var selectedTaskPrecaution: String? = null
    var selectedDate: String? = null
    var selectedTime: String? = null
    var selectedSimpleDate: String? = null
    var selectedSimpleTime: String? = null
    var lotoStatusCode: String? = null
    var lotoUid: String? = null
    var lotoMachineId: String? = null
    var lotoBatteryInfo: String? = null
    var lotoUserId: String? = null


    // 세션 데이터 초기화
    fun clear() {
        selectedBatteryInfo = null
        selectedFacilityName = null
        selectedMachineName = null
        selectedMachineId = null
        selectedTaskTemplateId = null
        selectedTaskPrecaution = null
        selectedDate = null
        selectedTime = null
        selectedSimpleDate = null
        selectedSimpleTime = null
        lotoStatusCode = null
        lotoUid = null
        lotoMachineId = null
        lotoBatteryInfo = null
        lotoUserId = null
    }
}
