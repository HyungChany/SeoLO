package com.c104.seolo.domain.core.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class CoreRequest {
    private String lockerUid;
    private Integer batteryInfo;
    private Long machineId;
    private String tokenValue;
    private Long taskTemplateId;
    private String taskPrecaution;
    private String endTime;

    @Builder
    public CoreRequest(String lockerUid, Integer batteryInfo, Long machineId, String tokenValue, Long taskTemplateId, String taskPrecaution, String endTime) {
        this.lockerUid = lockerUid;
        this.batteryInfo = batteryInfo;
        this.machineId = machineId;
        this.tokenValue = tokenValue;
        this.taskTemplateId = taskTemplateId;
        this.taskPrecaution = taskPrecaution;
        this.endTime = endTime;
    }
}
