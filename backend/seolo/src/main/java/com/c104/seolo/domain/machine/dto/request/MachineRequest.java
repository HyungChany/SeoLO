package com.c104.seolo.domain.machine.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class MachineRequest {
    private Long facilityId;
    private String machineName;
    private String machineCode;
    private String machineThum;
    private LocalDateTime introductionDate;
    private Long mainManagerId;
    private String mainManagerName;
    private Long subManagerId;
    private String subManagerName;
}
