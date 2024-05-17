package com.c104.seolo.domain.marker.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class MarkerInfoResponse {
    private final String machineNum;
    private final String machineName;
    private final String workerName;
    private final String estimatedEndTime;
    private final String content;
    private final String nowTaskStatus;

    @Builder
    public MarkerInfoResponse(String machineNum, String machineName, String workerName, String estimatedEndTime, String content, String nowTaskStatus) {
        this.machineNum = machineNum;
        this.machineName = machineName;
        this.workerName = workerName;
        this.estimatedEndTime = estimatedEndTime;
        this.content = content;
        this.nowTaskStatus = nowTaskStatus;
    }
}
