package com.c104.seolo.domain.machine.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    private LocalDateTime introductionDate;
    private Long mainManagerId;
    private String mainManagerName;
    private Long subManagerId;
    private String subManagerName;
}
