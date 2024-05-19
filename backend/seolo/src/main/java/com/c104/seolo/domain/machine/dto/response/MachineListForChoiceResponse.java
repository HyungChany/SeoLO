package com.c104.seolo.domain.machine.dto.response;

import com.c104.seolo.domain.machine.dto.MachineIdName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class MachineListForChoiceResponse {
    List<MachineIdName> machineIdNameList;

    public MachineListForChoiceResponse(List<MachineIdName> machineIdNameList) {
        this.machineIdNameList = machineIdNameList;
    }
}
