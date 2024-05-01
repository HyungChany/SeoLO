package com.c104.seolo.domain.machine.dto.info;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MachineListInfo {
    private Long facilityId;
    private String facilityName;
    private String machineSubcategory;
    private Long id;
    private String machineName;
    private String machineCode;
    private LocalDateTime introductionDate;
<<<<<<< HEAD
    private Long managerId;
    private String managerName;
    private String managerRole;
=======

    private Long mainManagerId;
    private String mainManagerName;

    private Long subManagerId;
    private String subManagerName;
>>>>>>> 3ebb31d8845f8fbbccba864913a8dbd805c251b9
}
