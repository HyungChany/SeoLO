package com.c104.seolo.domain.machine.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MachineListInfo {
    private Long facilityId;
    private String facilityName;

    private Long id;
    private String machineName;
    private String machineCode;
    private LocalDateTime introductionDate;

    private Long ManagerId;
    private String ManagerName;
    private String ManagerRole;
}
