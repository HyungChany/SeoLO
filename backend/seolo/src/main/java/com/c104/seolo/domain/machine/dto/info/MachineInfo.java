package com.c104.seolo.domain.machine.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class MachineInfo {
    private Long id;
    private String companyCode;
    private Long facilityId;
    private String facilityName;
    private String machineName;
    private String machineCode;
    private String machineThumbnail;
    private Date introductionDate;
}
