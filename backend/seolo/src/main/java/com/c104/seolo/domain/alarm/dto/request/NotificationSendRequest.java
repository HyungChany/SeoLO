package com.c104.seolo.domain.alarm.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationSendRequest {
    private Integer batteryInfo;
    private String workerName;
    private String facilityName;
    private String machineNumber;
    private String lockerUid;
    private String actType;

    @Builder
    public NotificationSendRequest(Integer batteryInfo, String workerName, String facilityName, String machineNumber, String lockerUid, String actType) {
        this.batteryInfo = batteryInfo;
        this.workerName = workerName;
        this.facilityName = facilityName;
        this.machineNumber = machineNumber;
        this.lockerUid = lockerUid;
        this.actType = actType;
    }
}
