package com.c104.seolo.domain.machine.service;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;

public interface MachineService {
    MachineDto findMachineByMachineId(String machineId);
    MachineListResponse findMachineByCompanyAndFacility(String companyCode, Long facilityId);
}
