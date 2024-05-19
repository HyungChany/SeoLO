package com.c104.seolo.domain.machine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class MachineListDto {
    private Long machineId;
    private Long facilityId;
    private String facilityName;
    private String machineName;
    private String machineCode;
    private Date introductionDate;
    private Long mainManagerId;
    private String mainManagerName;
    private Long subManagerId;
    private String subManagerName;
}
