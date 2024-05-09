package com.c104.seolo.domain.machine.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class MachineListInfo {
    private Long facilityId;
    private String facilityName;
    private Long id;
    private String machineName;
    private String machineCode;
    private Date introductionDate;
}
