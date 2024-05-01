package com.c104.seolo.domain.machine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class MachineDto {
    private Long id;
    private Long facilityId;
    private String facilityName;
    private String machineName;
    private String machineCode;
    private String machineThumbnail;
    private LocalDateTime introductionDate;
    private Long mainManagerId;
    private String mainManagerName;
    private Long subManagerId;
    private String subManagerName;
}
