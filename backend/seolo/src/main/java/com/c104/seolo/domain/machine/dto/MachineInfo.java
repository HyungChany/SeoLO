package com.c104.seolo.domain.machine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class MachineInfo {
    private Long id;
    private Long facilityId;
    private String facilityName;
    private String machineName;
    private String machineCode;
    private String machineThumbnail;
    private Date introductionDate;
    private Long mainManagerId;
    private String mainManagerName;
    private Long subManagerId;
    private String subManagerName;

    @Builder
    public MachineInfo(Long id, Long facilityId, String facilityName, String machineName, String machineCode, String machineThumbnail, Date introductionDate, Long mainManagerId, String mainManagerName, Long subManagerId, String subManagerName) {
        this.id = id;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.machineName = machineName;
        this.machineCode = machineCode;
        this.machineThumbnail = machineThumbnail;
        this.introductionDate = introductionDate;
        this.mainManagerId = mainManagerId;
        this.mainManagerName = mainManagerName;
        this.subManagerId = subManagerId;
        this.subManagerName = subManagerName;
    }
}
