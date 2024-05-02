package com.c104.seolo.domain.machine.service;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.request.MachineRequest;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;

public interface MachineService {
    MachineDto findMachineByMachineId(String companyCode, Long machineId);
    void createMachine(MachineRequest machineRequest, String companyCode);
    void updateMachineSpace(Long machineId ,Float latitude, Float longitude, String companyCode);
    MachineListResponse findMachineByCompanyAndFacility(String companyCode, Long facilityId);
}
