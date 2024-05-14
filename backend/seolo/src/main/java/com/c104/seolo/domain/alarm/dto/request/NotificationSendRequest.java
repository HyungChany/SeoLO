package com.c104.seolo.domain.alarm.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationSendRequest {
    private Long batteryInfo;
    private String workerName;
    private String facilityName;
    private String machineNumber;
    private String lockerUid;
    private String actType;
}
