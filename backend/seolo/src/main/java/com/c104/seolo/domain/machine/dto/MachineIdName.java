package com.c104.seolo.domain.machine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MachineIdName {
    private Long machineId;
    private String machineName;

    @Builder
    public MachineIdName(Long machineId, String machineName) {
        this.machineId = machineId;
        this.machineName = machineName;
    }
}
