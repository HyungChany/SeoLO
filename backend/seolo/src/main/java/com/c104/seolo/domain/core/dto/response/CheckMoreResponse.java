package com.c104.seolo.domain.core.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckMoreResponse {
    private String workerName;
    private String workerTeam;
    private String workerTitle;
    private String facilityName;
    private String machineName;
    private String machineNumber;

    @Builder
    public CheckMoreResponse(String workerName, String workerTeam, String workerTitle, String facilityName, String machineName, String machineNumber) {
        this.workerName = workerName;
        this.workerTeam = workerTeam;
        this.workerTitle = workerTitle;
        this.facilityName = facilityName;
        this.machineName = machineName;
        this.machineNumber = machineNumber;
    }
}
